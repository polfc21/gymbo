package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    @Query(value = "SELECT t.id, t.name, t.training_date FROM training t WHERE EXTRACT(MONTH FROM training_date) = ?1 AND EXTRACT(YEAR FROM training_date) = ?2", nativeQuery = true)
    List<TrainingEntity> findAllByMonthAndYear(Integer month, Integer year);
}
