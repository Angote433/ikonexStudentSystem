package com.school.ikonex.repo;

import com.school.ikonex.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer> {

    List<SchoolClass> findByAcademicYear(Integer academicYear);

    boolean existsByClassName(String className);
}