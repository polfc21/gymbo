package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<SerieEntity, Long> {
}
