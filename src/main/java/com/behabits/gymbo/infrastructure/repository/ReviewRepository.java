package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(value = "SELECT r.* FROM review r WHERE r.id = ?1 AND (r.reviewer_id = ?2 OR r.reviewed_id = ?2)", nativeQuery = true)
    ReviewEntity findByIdAndPlayerIsReviewerOrReviewed(Long id, Long playerId);
    List<ReviewEntity> findAllByReviewedId(Long reviewedId);
    ReviewEntity findByReviewerIdAndReviewedId(Long reviewerId, Long reviewedId);
}
