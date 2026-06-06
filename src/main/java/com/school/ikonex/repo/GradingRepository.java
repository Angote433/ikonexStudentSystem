package com.school.ikonex.repo;

import com.school.ikonex.model.GradingScale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface GradingRepository extends JpaRepository<GradingScale, Long> {

    @Query("SELECT g FROM GradingScale g WHERE :score BETWEEN g.minScore AND g.maxScore")
    Optional<GradingScale> findGradeForScore(@Param("score") BigDecimal score);
}
