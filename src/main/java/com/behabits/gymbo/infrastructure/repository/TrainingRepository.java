package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    @Query(value = "SELECT COUNT(*) FROM training WHERE EXTRACT(MONTH FROM training_date) = ?1", nativeQuery = true)
    List<TrainingEntity> findAllByMonth(Month month);
}
