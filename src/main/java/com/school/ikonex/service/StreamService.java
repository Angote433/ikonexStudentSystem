package com.school.ikonex.service;

import com.school.ikonex.dto.StreamDTO;
import com.school.ikonex.model.SchoolClass;
import com.school.ikonex.model.Stream;
import com.school.ikonex.repo.SchoolClassRepository;
import com.school.ikonex.repo.StreamRepository;
import com.school.ikonex.repo.SubjectRepository;
import com.school.ikonex.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamService {

    private final StreamRepository streamRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;

    public StreamService(StreamRepository streamRepository,
                         SchoolClassRepository schoolClassRepository,
                         SubjectRepository subjectRepository) {
        this.streamRepository = streamRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<StreamDTO> getAllStreams() {
        return streamRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public StreamDTO getStreamById(Integer id) {
        return toDTO(streamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found with id: " + id)));
    }

    public List<StreamDTO> getStreamsByClass(Integer classId) {
        return streamRepository.findBySchoolClass_ClassId(classId)
                .stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }


    public StreamDTO createStream(Integer classId, Stream stream) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));
        stream.setSchoolClass(schoolClass);
        return toDTO(streamRepository.save(stream));
    }

    public StreamDTO updateStream(Integer id, Stream updated) {
        Stream existing = streamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found with id: " + id));
        existing.setStreamName(updated.getStreamName());
        return toDTO(streamRepository.save(existing));
    }

    public void deleteStream(Integer id) {
        getStreamById(id);
        streamRepository.deleteById(id);
    }

    public StreamDTO assignSubjectToStream(Integer streamId, Integer subjectId) {
        Stream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        stream.getSubjects().add(subject);
        return toDTO(streamRepository.save(stream));
    }

    public int getStudentCount(Integer streamId) {
        return streamRepository.countStudentsInStream(streamId);
    }

    private StreamDTO toDTO(Stream stream) {
        return new StreamDTO(
                stream.getStreamId(),
                stream.getStreamName(),
                stream.getSchoolClass().getClassId(),
                stream.getSchoolClass().getClassName()
        );
    }
}
