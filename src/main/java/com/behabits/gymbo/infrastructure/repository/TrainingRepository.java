package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    @Query(value = "SELECT * FROM training WHERE EXTRACT(MONTH FROM training_date) = ?1 AND EXTRACT(YEAR FROM training_date) = ?2 AND player_id = ?3", nativeQuery = true)
    List<TrainingEntity> findAllByMonthAndYearAndPlayerId(Integer month, Integer year, Long playerId);
    TrainingEntity findByIdAndPlayerId(Long id, Long playerId);
}
