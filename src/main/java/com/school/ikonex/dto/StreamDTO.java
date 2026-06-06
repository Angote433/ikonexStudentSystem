package com.school.ikonex.dto;

public class StreamDTO {

    private Integer streamId;
    private String streamName;
    private Integer classId;
    private String className;

    public StreamDTO() {}

    public StreamDTO(Integer streamId, String streamName,
                     Integer classId, String className) {
        this.streamId = streamId;
        this.streamName = streamName;
        this.classId = classId;
        this.className = className;
    }

    public Integer getStreamId() { return streamId; }
    public void setStreamId(Integer streamId) { this.streamId = streamId; }

    public String getStreamName() { return streamName; }
    public void setStreamName(String streamName) { this.streamName = streamName; }

    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
