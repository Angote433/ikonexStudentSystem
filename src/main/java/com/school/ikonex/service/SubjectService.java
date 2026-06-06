package com.school.ikonex.service;

import com.school.ikonex.dto.SubjectDTO;
import com.school.ikonex.model.Subject;
import com.school.ikonex.repo.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public SubjectDTO getSubjectById(Integer id) {
        return toDTO(subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id)));
    }

    public SubjectDTO createSubject(Subject subject) {
        if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
            throw new RuntimeException("Subject code already exists");
        }
        return toDTO(subjectRepository.save(subject));
    }

    public SubjectDTO updateSubject(Integer id, Subject updated) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        existing.setSubjectName(updated.getSubjectName());
        existing.setSubjectCode(updated.getSubjectCode());
        return toDTO(subjectRepository.save(existing));
    }

    public void deleteSubject(Integer id) {
        getSubjectById(id);
        subjectRepository.deleteById(id);
    }
    private SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(
                subject.getSubjectId(),
                subject.getSubjectName(),
                subject.getSubjectCode()
        );
    }
}
