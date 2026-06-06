package com.school.ikonex.dto;

import java.math.BigDecimal;

public class StudentScoreDTO {

    private Long scoreId;
    private Integer studentId;
    private String studentName;
    private String admissionNumber;
    private Integer subjectId;
    private String subjectName;
    private BigDecimal examScore;
    private BigDecimal catScore;
    private BigDecimal totalScore;
    private Integer academicYear;
    private String term;
    private String grade;
    private String remarks;

    public StudentScoreDTO() {}

    public StudentScoreDTO(Long scoreId, Integer studentId, String studentName,
                           String admissionNumber, Integer subjectId, String subjectName,
                           BigDecimal examScore, BigDecimal catScore, BigDecimal totalScore,
                           Integer academicYear, String term, String grade, String remarks) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.admissionNumber = admissionNumber;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.examScore = examScore;
        this.catScore = catScore;
        this.totalScore = totalScore;
        this.academicYear = academicYear;
        this.term = term;
        this.grade = grade;
        this.remarks = remarks;
    }

    public Long getScoreId() { return scoreId; }
    public void setScoreId(Long scoreId) { this.scoreId = scoreId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getAdmissionNumber() { return admissionNumber; }
    public void setAdmissionNumber(String admissionNumber) { this.admissionNumber = admissionNumber; }

    public Integer getSubjectId() { return subjectId; }
    public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public BigDecimal getExamScore() { return examScore; }
    public void setExamScore(BigDecimal examScore) { this.examScore = examScore; }

    public BigDecimal getCatScore() { return catScore; }
    public void setCatScore(BigDecimal catScore) { this.catScore = catScore; }

    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
