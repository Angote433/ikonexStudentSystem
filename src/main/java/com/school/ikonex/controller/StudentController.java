package com.school.ikonex.controller;

import com.school.ikonex.dto.StudentDTO;
import com.school.ikonex.model.Student;
import com.school.ikonex.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/stream/{streamId}")
    public ResponseEntity<List<StudentDTO>> getStudentsByStream(@PathVariable Integer streamId) {
        return ResponseEntity.ok(studentService.getStudentsByStream(streamId));
    }

    @PostMapping("/stream/{streamId}")
    public ResponseEntity<StudentDTO> createStudent(@PathVariable Integer streamId,
                                                 @RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(streamId, student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Integer id,
                                                 @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
