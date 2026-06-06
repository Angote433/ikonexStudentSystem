package com.school.ikonex.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name", nullable = false, length = 20)
    private String subjectName;

    @Column(name = "subject_code", unique = true, nullable = false, length = 20)
    private String subjectCode;

    @ManyToMany(mappedBy = "subjects")
    private List<Stream> streams;

    public Subject() {}

    public Subject(Integer subjectId, String subjectName,
                   String subjectCode, List<Stream> streams) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.streams = streams;
    }

    public Integer getSubjectId() { return subjectId; }
    public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public List<Stream> getStreams() { return streams; }
    public void setStreams(List<Stream> streams) { this.streams = streams; }
}