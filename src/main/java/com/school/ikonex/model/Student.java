package com.school.ikonex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "admission_number", unique = true, nullable = false, length = 20)
    private String admissionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    private Stream stream;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentScore> scores;

    public Student() {}

    public Student(Integer studentId, String firstName, String lastName,
                   String admissionNumber, Stream stream, List<StudentScore> scores) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionNumber = admissionNumber;
        this.stream = stream;
        this.scores = scores;
    }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAdmissionNumber() { return admissionNumber; }
    public void setAdmissionNumber(String admissionNumber) { this.admissionNumber = admissionNumber; }

    public Stream getStream() { return stream; }
    public void setStream(Stream stream) { this.stream = stream; }

    public List<StudentScore> getScores() { return scores; }
    public void setScores(List<StudentScore> scores) { this.scores = scores; }
}
