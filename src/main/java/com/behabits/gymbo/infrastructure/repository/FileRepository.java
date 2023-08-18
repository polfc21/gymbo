package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByIdAndPlayerId(Long id, Long playerId);
}
