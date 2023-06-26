package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findAllByPlayerId(Long playerId);
    TokenEntity findByTokenAndPlayerId(String token, Long playerId);
}
