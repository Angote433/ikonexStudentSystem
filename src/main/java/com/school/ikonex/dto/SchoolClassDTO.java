package com.school.ikonex.dto;

public class SchoolClassDTO {

    private Integer classId;
    private String className;
    private Integer gradeLevel;
    private Integer academicYear;

    public SchoolClassDTO() {}

    public SchoolClassDTO(Integer classId, String className,
                          Integer gradeLevel, Integer academicYear) {
        this.classId = classId;
        this.className = className;
        this.gradeLevel = gradeLevel;
        this.academicYear = academicYear;
    }

    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Integer getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; }

    public Integer getAcademicYear() { return academicYear; }
    public void setAcademicYear(Integer academicYear) { this.academicYear = academicYear; }
}
