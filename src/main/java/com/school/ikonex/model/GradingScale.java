package com.school.ikonex.model;


import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "grading_scales")
public class GradingScale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_score", precision = 5, scale = 2)
    private BigDecimal minScore;

    @Column(name = "max_score", precision = 5, scale = 2)
    private BigDecimal maxScore;

    @Column(name = "grade", length = 5)
    private String grade;

    @Column(name = "points")
    private Integer points;

    @Column(name = "remarks", length = 50)
    private String remarks;

    public GradingScale() {}

    public GradingScale(Long id, BigDecimal minScore, BigDecimal maxScore,
                        String grade, Integer points, String remarks) {
        this.id = id;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.grade = grade;
        this.points = points;
        this.remarks = remarks;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getMinScore() { return minScore; }
    public void setMinScore(BigDecimal minScore) { this.minScore = minScore; }

    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
