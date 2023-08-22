package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;

public class UserEntityRepository {

    public UserEntity getUser() {
        return UserEntity.builder()
                .id(1L)
                .username("pol")
                .password("password")
                .email("polfarreny@gmail.com")
                .firstName("Pol")
                .lastName("Farreny Capdevila")
                .enabled(true)
                .build();
    }

    public UserEntity getReviewer() {
        return UserEntity.builder()
                .id(2L)
                .username("reviewer")
                .password("password")
                .email("reviewer@gmail.com")
                .firstName("Reviewer")
                .lastName("Reviewer")
                .enabled(true)
                .build();
    }

    public UserEntity getReviewed() {
        return UserEntity.builder()
                .id(3L)
                .username("reviewed")
                .password("password")
                .email("reviewed@gmail.com")
                .firstName("Reviewed")
                .lastName("Reviewed")
                .enabled(true)
                .build();
    }
}
