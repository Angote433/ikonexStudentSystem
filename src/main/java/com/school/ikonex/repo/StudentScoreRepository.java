package com.school.ikonex.repo;

import com.school.ikonex.model.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {

    // all scores for one student in a specific term
    @Query("SELECT ss FROM StudentScore ss WHERE ss.student.studentId = :studentId AND ss.academicYear = :academicYear AND ss.term = :term")
    List<StudentScore> findByStudentScoreByIdAndYear(
            @Param("studentId") Integer studentId,
            @Param("academicYear") Integer academicYear,
            @Param("term") String term
    );

    // all scores for a specific subject in a stream (for class performance)
    @Query("SELECT ss FROM StudentScore ss " +
            "WHERE ss.subject.subjectId = :subjectId " +
            "AND ss.student.stream.streamId = :streamId " +
            "AND ss.academicYear = :year " +
            "AND ss.term = :term")
    List<StudentScore> findBySubjectAndStream(
            @Param("subjectId") Integer subjectId,
            @Param("streamId") Integer streamId,
            @Param("year") Integer academicYear,
            @Param("term") String term);

    // check if score already exists before saving
    boolean existsByStudent_StudentIdAndSubject_SubjectIdAndAcademicYearAndTerm(
            Integer studentId, Integer subjectId, Integer academicYear, String term);

    // fetch all scores for a stream in a term (used for ranking)
    @Query("SELECT ss FROM StudentScore ss " +
            "WHERE ss.student.stream.streamId = :streamId " +
            "AND ss.academicYear = :year " +
            "AND ss.term = :term")
    List<StudentScore> findAllByStreamAndTerm(
            @Param("streamId") Integer streamId,
            @Param("year") Integer academicYear,
            @Param("term") String term);
}