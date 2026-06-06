package com.school.ikonex.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "stream")
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stream_id")
    private Integer streamId;

    @Column(name = "stream_name", nullable = false, length = 20)
    private String streamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "stream")
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "stream_subjects",
            joinColumns = @JoinColumn(name = "stream_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    public Stream() {}

    public Stream(Integer streamId, String streamName, SchoolClass schoolClass,
                  List<Student> students, List<Subject> subjects) {
        this.streamId = streamId;
        this.streamName = streamName;
        this.schoolClass = schoolClass;
        this.students = students;
        this.subjects = subjects;
    }

    public Integer getStreamId() { return streamId; }
    public void setStreamId(Integer streamId) { this.streamId = streamId; }

    public String getStreamName() { return streamName; }
    public void setStreamName(String streamName) { this.streamName = streamName; }

    public SchoolClass getSchoolClass() { return schoolClass; }
    public void setSchoolClass(SchoolClass schoolClass) { this.schoolClass = schoolClass; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }
}
