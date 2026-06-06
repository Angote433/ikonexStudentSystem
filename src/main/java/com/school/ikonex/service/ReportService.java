package com.school.ikonex.service;

import com.school.ikonex.model.GradingScale;
import com.school.ikonex.model.Student;
import com.school.ikonex.model.StudentScore;
import com.school.ikonex.repo.GradingRepository;
import com.school.ikonex.repo.StudentRepository;
import com.school.ikonex.repo.StudentScoreRepository;
import org.springframework.stereotype.Service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    private final StudentRepository studentRepository;
    private final StudentScoreRepository scoreRepository;
    private final GradingRepository gradingScaleRepository;
    private final StudentScoreService studentScoreService;

    public ReportService(StudentRepository studentRepository,
                         StudentScoreRepository scoreRepository,
                         GradingRepository gradingScaleRepository,
                         StudentScoreService studentScoreService) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
        this.gradingScaleRepository = gradingScaleRepository;
        this.studentScoreService = studentScoreService;
    }

    public byte[] generateStudentReportCard(Integer studentId,
                                            Integer academicYear,
                                            String term) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        System.out.println("Student Id: " + studentId);
        System.out.println("Year: "+ academicYear);
        System.out.println("Term: "+ term);


        List<StudentScore> scores = scoreRepository
                .findByStudentScoreByIdAndYear(studentId, academicYear, term);
        System.out.println(scores.size());


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // fonts
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.WHITE);
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK);
            Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);
            Font smallFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY);
            Font tableHeaderFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
            Font tableBodyFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);

            // school header banner
            PdfPTable banner = new PdfPTable(1);
            banner.setWidthPercentage(100);
            PdfPCell bannerCell = new PdfPCell(new Phrase("IKONEX ACADEMY", titleFont));
            bannerCell.setBackgroundColor(new Color(26, 35, 126));
            bannerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            bannerCell.setPadding(14);
            bannerCell.setBorder(Rectangle.NO_BORDER);
            banner.addCell(bannerCell);
            document.add(banner);

            // subtitle
            Paragraph subtitle = new Paragraph("STUDENT REPORT CARD", headerFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingBefore(4);
            document.add(subtitle);

            Paragraph termLine = new Paragraph(term + "  |  " + academicYear, smallFont);
            termLine.setAlignment(Element.ALIGN_CENTER);
            termLine.setSpacingAfter(12);
            document.add(termLine);

            // divider
            document.add(new Paragraph("_____________________________________________" +
                    "______________________________"));

            // student info table
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10);
            infoTable.setSpacingAfter(10);

            addInfoRow(infoTable, "Student Name:",
                    student.getFirstName() + " " + student.getLastName(),
                    headerFont, normalFont);
            addInfoRow(infoTable, "Admission Number:",
                    student.getAdmissionNumber(), headerFont, normalFont);
            addInfoRow(infoTable, "Stream:",
                    student.getStream() != null ?
                            student.getStream().getStreamName() : "N/A",
                    headerFont, normalFont);
            addInfoRow(infoTable, "Class:",
                    student.getStream() != null ?
                            student.getStream().getSchoolClass().getClassName() : "N/A",
                    headerFont, normalFont);

            document.add(infoTable);

            // scores table
            PdfPTable scoresTable = new PdfPTable(6);
            scoresTable.setWidthPercentage(100);
            scoresTable.setSpacingBefore(10);
            scoresTable.setWidths(new float[]{3f, 1.5f, 1.5f, 1.5f, 1f, 2f});

            // scores table header
            String[] headers = {"Subject", "Exam", "CAT", "Total", "Grade", "Remarks"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, tableHeaderFont));
                cell.setBackgroundColor(new Color(26, 35, 126));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                scoresTable.addCell(cell);
            }

            // scores rows
            BigDecimal grandTotal = BigDecimal.ZERO;
            boolean alternate = false;

            for (StudentScore score : scores) {
                Color rowColor = alternate ? new Color(232, 234, 246) : Color.WHITE;
                alternate = !alternate;

                GradingScale grading = gradingScaleRepository
                        .findGradeForScore(score.getTotalScore())
                        .orElse(null);

                addScoreRow(scoresTable, tableBodyFont, rowColor,
                        score.getSubject().getSubjectName(),
                        score.getExamScore().toString(),
                        score.getCatScore().toString(),
                        score.getTotalScore().toString(),
                        grading != null ? grading.getGrade() : "N/A",
                        grading != null ? grading.getRemarks() : "N/A");

                grandTotal = grandTotal.add(score.getTotalScore());
            }

            document.add(scoresTable);

            // summary section
            BigDecimal average = scores.isEmpty() ? BigDecimal.ZERO :
                    grandTotal.divide(BigDecimal.valueOf(scores.size()), 2, RoundingMode.HALF_UP);

            GradingScale overallGrade = gradingScaleRepository
                    .findGradeForScore(average).orElse(null);

            // get position from ranking
            List<java.util.Map<String, Object>> ranking =
                    studentScoreService.rankStudentsInStream(
                            student.getStream().getStreamId(), academicYear, term);

            int position = ranking.stream()
                    .filter(r -> r.get("studentId").equals(studentId))
                    .mapToInt(r -> (int) r.get("position"))
                    .findFirst()
                    .orElse(0);

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(60);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryTable.setSpacingBefore(12);

            addInfoRow(summaryTable, "Total Marks:", grandTotal.toString(),
                    headerFont, normalFont);
            addInfoRow(summaryTable, "Average Score:", average.toString(),
                    headerFont, normalFont);
            addInfoRow(summaryTable, "Overall Grade:",
                    overallGrade != null ? overallGrade.getGrade() : "N/A",
                    headerFont, normalFont);
            addInfoRow(summaryTable, "Class Position:",
                    position + " / " + ranking.size(),
                    headerFont, normalFont);

            document.add(summaryTable);

            // grading key
            document.add(new Paragraph(" "));
            Paragraph gradingKey = new Paragraph("GRADING SCALE", headerFont);
            gradingKey.setSpacingBefore(10);
            document.add(gradingKey);

            PdfPTable keyTable = new PdfPTable(4);
            keyTable.setWidthPercentage(100);
            keyTable.setSpacingBefore(6);

            String[] keyHeaders = {"Grade", "Min Score", "Max Score", "Remarks"};
            for (String h : keyHeaders) {
                PdfPCell cell = new PdfPCell(new Phrase(h, tableHeaderFont));
                cell.setBackgroundColor(new Color(26, 35, 126));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                keyTable.addCell(cell);
            }

            List<GradingScale> allGrades = gradingScaleRepository.findAll();
            for (GradingScale g : allGrades) {
                keyTable.addCell(new PdfPCell(new Phrase(g.getGrade(), tableBodyFont)));
                keyTable.addCell(new PdfPCell(new Phrase(g.getMinScore().toString(), tableBodyFont)));
                keyTable.addCell(new PdfPCell(new Phrase(g.getMaxScore().toString(), tableBodyFont)));
                keyTable.addCell(new PdfPCell(new Phrase(g.getRemarks(), tableBodyFont)));
            }

            document.add(keyTable);

            // signature line
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph signature = new Paragraph(
                    "Class Teacher: ___________________          " +
                            "Principal: ___________________", smallFont);
            signature.setSpacingBefore(20);
            document.add(signature);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report card: " + e.getMessage());
        } finally {
            document.close();
        }

        return out.toByteArray();
    }

    // helper — two column info row
    private void addInfoRow(PdfPTable table, String label, String value,
                            Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(4);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(4);
        table.addCell(valueCell);
    }

    // helper — score row
    private void addScoreRow(PdfPTable table, Font font, Color bgColor,
                             String... values) {
        for (String val : values) {
            PdfPCell cell = new PdfPCell(new Phrase(val, font));
            cell.setBackgroundColor(bgColor);
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

}
