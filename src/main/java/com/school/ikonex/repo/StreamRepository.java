package com.school.ikonex.repo;

import com.school.ikonex.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer> {

    List<Stream> findBySchoolClass_ClassId(Integer classId);

    boolean existsByStreamName(String streamName);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.stream.streamId = :streamId")
    int countStudentsInStream(@Param("streamId") Integer streamId);
}
