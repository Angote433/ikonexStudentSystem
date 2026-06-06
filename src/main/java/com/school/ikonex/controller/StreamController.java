package com.school.ikonex.controller;

import com.school.ikonex.dto.StreamDTO;
import com.school.ikonex.model.Stream;
import com.school.ikonex.service.StreamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streams")
public class StreamController {

    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping
    public ResponseEntity<List<StreamDTO>> getAllStreams() {
        return ResponseEntity.ok(streamService.getAllStreams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamDTO> getStreamById(@PathVariable Integer id) {
        return ResponseEntity.ok(streamService.getStreamById(id));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<StreamDTO>> getStreamsByClass(@PathVariable Integer classId) {
        return ResponseEntity.ok(streamService.getStreamsByClass(classId));
    }

    @GetMapping("/{id}/student-count")
    public ResponseEntity<Integer> getStudentCount(@PathVariable Integer id) {
        return ResponseEntity.ok(streamService.getStudentCount(id));
    }

    @PostMapping("/class/{classId}")
    public ResponseEntity<StreamDTO> createStream(@PathVariable Integer classId,
                                               @RequestBody Stream stream) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(streamService.createStream(classId, stream));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamDTO> updateStream(@PathVariable Integer id,
                                               @RequestBody Stream stream) {
        return ResponseEntity.ok(streamService.updateStream(id, stream));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStream(@PathVariable Integer id) {
        streamService.deleteStream(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{streamId}/subjects/{subjectId}")
    public ResponseEntity<StreamDTO> assignSubject(@PathVariable Integer streamId,
                                                @PathVariable Integer subjectId) {
        return ResponseEntity.ok(streamService.assignSubjectToStream(streamId, subjectId));
    }
}