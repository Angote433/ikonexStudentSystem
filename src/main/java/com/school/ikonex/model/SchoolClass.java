package com.school.ikonex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "school_class")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "class_name", nullable = false, length = 20)
    private String className;

    @Column(name = "grade_level", nullable = false)
    private Integer gradeLevel;

    @Column(name = "academic_year", nullable = false)
    private Integer academicYear;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Stream> streams;

    public SchoolClass() {}

    public SchoolClass(Integer classId, String className, Integer gradeLevel,
                       Integer academicYear, List<Stream> streams) {
        this.classId = classId;
        this.className = className;
        this.gradeLevel = gradeLevel;
        this.academicYear = academicYear;
        this.streams = streams;
    }

    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Integer getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }

    public List<Stream> getStreams() { return streams; }
    public void setStreams(List<Stream> streams) { this.streams = streams; }
}
