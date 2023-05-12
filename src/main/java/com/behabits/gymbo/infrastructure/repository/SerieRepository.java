package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<SerieEntity, Long> {
    @Query(value = "SELECT s.* FROM serie s JOIN exercise e on s.exercise_id = e.id WHERE s.id = ?1 AND e.player_id = ?2", nativeQuery = true)
    SerieEntity findByIdAndPlayerId(Long id, Long playerId);
}
