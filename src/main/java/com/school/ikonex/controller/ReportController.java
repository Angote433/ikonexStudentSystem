package com.school.ikonex.controller;

import com.school.ikonex.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report-card/{studentId}")
    public ResponseEntity<byte[]> generateReportCard(
            @PathVariable Integer studentId,
            @RequestParam Integer academicYear,
            @RequestParam String term) {

        byte[] pdf = reportService.generateStudentReportCard(
                studentId, academicYear, term);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report-card-" + studentId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
