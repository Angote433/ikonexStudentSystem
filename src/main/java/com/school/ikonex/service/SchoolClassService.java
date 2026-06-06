package com.school.ikonex.service;

import com.school.ikonex.dto.SchoolClassDTO;
import com.school.ikonex.model.SchoolClass;
import com.school.ikonex.repo.SchoolClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public List<SchoolClassDTO> getAllClasses() {
        return schoolClassRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public SchoolClassDTO getClassById(Integer id) {
        return toDTO(schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id)));
    }

    public SchoolClassDTO createClass(SchoolClass schoolClass) {
        if (schoolClassRepository.existsByClassName(schoolClass.getClassName())) {
            throw new RuntimeException("Class with this name already exists");
        }
        return toDTO(schoolClassRepository.save(schoolClass));
    }

    public SchoolClassDTO updateClass(Integer id, SchoolClass updated) {
        SchoolClass existing = schoolClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        existing.setClassName(updated.getClassName());
        existing.setGradeLevel(updated.getGradeLevel());
        existing.setAcademicYear(updated.getAcademicYear());
        return toDTO(schoolClassRepository.save(existing));
    }

    public void deleteClass(Integer id) {
        getClassById(id);
        schoolClassRepository.deleteById(id);
    }
    private SchoolClassDTO toDTO(SchoolClass sc) {
        return new SchoolClassDTO(
                sc.getClassId(),
                sc.getClassName(),
                sc.getGradeLevel(),
                sc.getAcademicYear()
        );
    }
}
