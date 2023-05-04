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

}
