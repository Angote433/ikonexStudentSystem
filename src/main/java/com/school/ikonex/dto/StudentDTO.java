package com.school.ikonex.dto;

public class StudentDTO {

    private Integer studentId;
    private String firstName;
    private String lastName;
    private String admissionNumber;
    private Integer streamId;
    private String streamName;
    private String className;

    public StudentDTO() {}

    public StudentDTO(Integer studentId, String firstName, String lastName,
                      String admissionNumber, Integer streamId,
                      String streamName, String className) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admissionNumber = admissionNumber;
        this.streamId = streamId;
        this.streamName = streamName;
        this.className = className;
    }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAdmissionNumber() { return admissionNumber; }
    public void setAdmissionNumber(String admissionNumber) { this.admissionNumber = admissionNumber; }

    public Integer getStreamId() { return streamId; }
    public void setStreamId(Integer streamId) { this.streamId = streamId; }

    public String getStreamName() { return streamName; }
    public void setStreamName(String streamName) { this.streamName = streamName; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
