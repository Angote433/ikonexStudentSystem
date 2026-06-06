package com.school.ikonex.service;

import com.school.ikonex.dto.StudentDTO;
import com.school.ikonex.model.Student;
import com.school.ikonex.model.Stream;
import com.school.ikonex.repo.StreamRepository;
import com.school.ikonex.repo.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StreamRepository streamRepository;

    public StudentService(StudentRepository studentRepository,
                          StreamRepository streamRepository) {
        this.studentRepository = studentRepository;
        this.streamRepository = streamRepository;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public StudentDTO getStudentById(Integer id) {
        return toDTO(studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id)));
    }

    public List<StudentDTO> getStudentsByStream(Integer streamId) {
        return studentRepository.findByStream_StreamId(streamId)
                .stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public StudentDTO createStudent(Integer streamId, Student student) {
        if (studentRepository.existsByAdmissionNumber(student.getAdmissionNumber())) {
            throw new RuntimeException("Admission number already exists");
        }
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found with id: " + streamId));
        student.setStream(stream);
        return toDTO(studentRepository.save(student));
    }


    public StudentDTO updateStudent(Integer id, Student updated) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setAdmissionNumber(updated.getAdmissionNumber());
        if (updated.getStream() != null) {
            Stream stream = streamRepository.findById(updated.getStream().getStreamId())
                    .orElseThrow(() -> new RuntimeException("Stream not found"));
            existing.setStream(stream);
        }
        return toDTO(studentRepository.save(existing));
    }

    public void deleteStudent(Integer id) {
        getStudentById(id);
        studentRepository.deleteById(id);
    }

    private StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getAdmissionNumber(),
                student.getStream() != null ? student.getStream().getStreamId() : null,
                student.getStream() != null ? student.getStream().getStreamName() : null,
                student.getStream() != null ? student.getStream().getSchoolClass().getClassName() : null
        );
    }
}
