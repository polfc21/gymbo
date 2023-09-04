package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    @Query(value = "SELECT p.* FROM player p JOIN location l ON p.id = l.player_id WHERE ST_DWithin(ST_Transform(l.geometry, 32631), ST_Transform((SELECT l.geometry FROM location l WHERE l.player_id = ?1), 32631), ?2 * 1000) AND l.player_id != ?1 ORDER BY ST_Distance(geometry, (SELECT l.geometry FROM location l WHERE l.player_id = ?1))", nativeQuery = true)
    List<UserEntity> findUsersInKilometersOrderedByDistanceFromPlayerId(Long playerId, Double kilometers);
}
