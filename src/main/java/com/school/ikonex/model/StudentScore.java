package com.school.ikonex.model;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "student_scores")
public class StudentScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long scoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "exam_score", precision = 5, scale = 2)
    private BigDecimal examScore;

    @Column(name = "cat_score", precision = 5, scale = 2)
    private BigDecimal catScore;

    @Column(name = "total_score", precision = 5, scale = 2, insertable = false, updatable = false)
    private BigDecimal totalScore;

    @Column(name = "academic_year")
    private Integer academicYear;

    @Column(name = "term", length = 20)
    private String term;

    public StudentScore() {}

    public StudentScore(Long scoreId, Student student, Subject subject,
                        BigDecimal examScore, BigDecimal catScore,
                        BigDecimal totalScore, Integer academicYear, String term) {
        this.scoreId = scoreId;
        this.student = student;
        this.subject = subject;
        this.examScore = examScore;
        this.catScore = catScore;
        this.totalScore = totalScore;
        this.academicYear = academicYear;
        this.term = term;
    }

    public Long getScoreId() { return scoreId; }
    public void setScoreId(Long scoreId) { this.scoreId = scoreId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

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
}
