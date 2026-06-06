package com.school.ikonex.controller;

import com.school.ikonex.dto.StudentScoreDTO;
import com.school.ikonex.model.StudentScore;
import com.school.ikonex.service.StudentScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
public class StudentScoreController {

    private final StudentScoreService scoreService;

    public StudentScoreController(StudentScoreService scoreService) {
        this.scoreService = scoreService;
    }

    // get all scores for a student in a specific term
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentScoreDTO>> getStudentScores(
            @PathVariable Integer studentId,
            @RequestParam Integer academicYear,
            @RequestParam String term) {
        return ResponseEntity.ok(
                scoreService.getStudentScores(studentId, academicYear, term));
    }

    // get class performance for a subject
    @GetMapping("/subject/{subjectId}/stream/{streamId}")
    public ResponseEntity<List<StudentScoreDTO>> getClassPerformance(
            @PathVariable Integer subjectId,
            @PathVariable Integer streamId,
            @RequestParam Integer academicYear,
            @RequestParam String term) {
        return ResponseEntity.ok(
                scoreService.getClassPerformanceBySubject(
                        subjectId, streamId, academicYear, term));
    }

    // get ranked results for a stream
    @GetMapping("/results/stream/{streamId}")
    public ResponseEntity<List<Map<String, Object>>> getStreamResults(
            @PathVariable Integer streamId,
            @RequestParam Integer academicYear,
            @RequestParam String term) {
        return ResponseEntity.ok(
                scoreService.rankStudentsInStream(streamId, academicYear, term));
    }

    // record a new score
    @PostMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<StudentScoreDTO> saveScore(
            @PathVariable Integer studentId,
            @PathVariable Integer subjectId,
            @RequestBody StudentScore score) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scoreService.saveScore(studentId, subjectId, score));
    }

    // update an existing score
    @PutMapping("/{scoreId}")
    public ResponseEntity<StudentScoreDTO> updateScore(
            @PathVariable Long scoreId,
            @RequestBody StudentScore score) {
        return ResponseEntity.ok(scoreService.updateScore(scoreId, score));
    }
}
