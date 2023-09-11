package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    @Query(value = "SELECT l.* FROM link l JOIN publication p on l.publication_id = p.id WHERE l.id = ?1 AND p.player_id = ?2", nativeQuery = true)
    LinkEntity findByIdAndPlayerId(Long id, Long playerId);
}
