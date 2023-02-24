package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    List<TrainingEntity> findAllByDate_Month(Month month);
}
