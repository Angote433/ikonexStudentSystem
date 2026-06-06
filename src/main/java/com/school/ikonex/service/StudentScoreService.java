package com.school.ikonex.service;

import com.school.ikonex.dto.StudentScoreDTO;
import com.school.ikonex.model.GradingScale;
import com.school.ikonex.model.Student;
import com.school.ikonex.model.StudentScore;
import com.school.ikonex.model.Subject;
import com.school.ikonex.repo.GradingRepository;
import com.school.ikonex.repo.StudentRepository;
import com.school.ikonex.repo.StudentScoreRepository;
import com.school.ikonex.repo.SubjectRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentScoreService {

    private final StudentScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final GradingRepository gradingScaleRepository;

    public StudentScoreService(StudentScoreRepository scoreRepository,
                               StudentRepository studentRepository,
                               SubjectRepository subjectRepository,
                               GradingRepository gradingScaleRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.gradingScaleRepository = gradingScaleRepository;
    }

    public StudentScoreDTO saveScore(Integer studentId, Integer subjectId,
                                  StudentScore score) {
        // prevent duplicate
        if (scoreRepository.existsByStudent_StudentIdAndSubject_SubjectIdAndAcademicYearAndTerm(
                studentId, subjectId, score.getAcademicYear(), score.getTerm())) {
            throw new RuntimeException("Score already exists for this student, subject and term");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        score.setStudent(student);
        score.setSubject(subject);
        return toDTO(scoreRepository.save(score));
    }

    public StudentScoreDTO updateScore(Long scoreId, StudentScore updated) {
        StudentScore existing = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score not found with id: " + scoreId));
        existing.setExamScore(updated.getExamScore());
        existing.setCatScore(updated.getCatScore());
        existing.setTerm(updated.getTerm());
        existing.setAcademicYear(updated.getAcademicYear());
        return toDTO(scoreRepository.save(existing));
    }

    // single student results for a term
    public List<StudentScoreDTO> getStudentScores(Integer studentId,
                                               Integer academicYear, String term) {
        return scoreRepository.findByStudentScoreByIdAndYear(
                studentId, academicYear, term).stream().map(this ::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    // class performance for a specific subject
    public List<StudentScoreDTO> getClassPerformanceBySubject(Integer subjectId,
                                                           Integer streamId,
                                                           Integer academicYear,
                                                           String term) {

        return scoreRepository.findBySubjectAndStream(subjectId, streamId, academicYear, term)
                .stream().map(this :: toDTO).collect(java.util.stream.Collectors.toList());
    }

    // rank all students in a stream for a term
    public List<Map<String, Object>> rankStudentsInStream(Integer streamId,
                                                          Integer academicYear,
                                                          String term) {
        List<StudentScore> allScores = scoreRepository.findAllByStreamAndTerm(
                streamId, academicYear, term);

        // group scores by student
        Map<Student, List<StudentScore>> grouped = allScores.stream()
                .collect(Collectors.groupingBy(StudentScore::getStudent));

        List<Map<String, Object>> results = new ArrayList<>();

        for (Map.Entry<Student, List<StudentScore>> entry : grouped.entrySet()) {
            Student student = entry.getKey();
            List<StudentScore> scores = entry.getValue();

            BigDecimal total = scores.stream()
                    .map(StudentScore::getTotalScore)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal average = total.divide(
                    BigDecimal.valueOf(scores.size()), 2, RoundingMode.HALF_UP);

            GradingScale grade = gradingScaleRepository.findGradeForScore(average)
                    .orElseThrow(() -> new RuntimeException("No grade found for score: " + average));

            Map<String, Object> result = new java.util.HashMap<>();
            result.put("studentId", student.getStudentId());
            result.put("name", student.getFirstName() + " " + student.getLastName());
            result.put("admissionNumber", student.getAdmissionNumber());
            result.put("total", total);
            result.put("average", average);
            result.put("grade", grade.getGrade());
            result.put("remarks", grade.getRemarks());
            results.add(result);
        }

        // sort by total descending and assign positions
        results.sort((a, b) -> ((BigDecimal) b.get("total"))
                .compareTo((BigDecimal) a.get("total")));

        for (int i = 0; i < results.size(); i++) {
            results.get(i).put("position", i + 1);
        }

        return results;
    }

    public String getGradeForScore(BigDecimal score) {
        return gradingScaleRepository.findGradeForScore(score)
                .map(GradingScale::getGrade)
                .orElse("N/A");
    }
    private StudentScoreDTO toDTO(StudentScore score) {
        String grade = gradingScaleRepository
                .findGradeForScore(score.getTotalScore())
                .map(GradingScale::getGrade)
                .orElse("N/A");

        String remarks = gradingScaleRepository
                .findGradeForScore(score.getTotalScore())
                .map(GradingScale::getRemarks)
                .orElse("N/A");

        return new StudentScoreDTO(
                score.getScoreId(),
                score.getStudent().getStudentId(),
                score.getStudent().getFirstName() + " " + score.getStudent().getLastName(),
                score.getStudent().getAdmissionNumber(),
                score.getSubject().getSubjectId(),
                score.getSubject().getSubjectName(),
                score.getExamScore(),
                score.getCatScore(),
                score.getTotalScore(),
                score.getAcademicYear(),
                score.getTerm(),
                grade,
                remarks
        );
    }
}
