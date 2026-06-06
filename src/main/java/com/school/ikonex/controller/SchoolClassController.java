package com.school.ikonex.controller;

import com.school.ikonex.dto.SchoolClassDTO;
import com.school.ikonex.model.SchoolClass;
import com.school.ikonex.service.SchoolClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:3000")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getAllClasses() {
        return ResponseEntity.ok(schoolClassService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> getClassById(@PathVariable Integer id) {
        return ResponseEntity.ok(schoolClassService.getClassById(id));
    }

    @PostMapping
    public ResponseEntity<SchoolClassDTO> createClass(@RequestBody SchoolClass schoolClass) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(schoolClassService.createClass(schoolClass));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> updateClass(@PathVariable Integer id,
                                                   @RequestBody SchoolClass schoolClass) {
        return ResponseEntity.ok(schoolClassService.updateClass(id, schoolClass));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        schoolClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
