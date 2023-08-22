package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserModelRepository {

    public User getUser() {
        return User.builder()
                .id(1L)
                .username("pol")
                .firstName("Pol")
                .lastName("Farreny Capdevila")
                .email("polfarreny@gmail.com")
                .build();
    }

    public User getReviewer() {
        return User.builder()
                .id(2L)
                .username("reviewer")
                .password("password")
                .email("reviewer@gmail.com")
                .firstName("Reviewer")
                .lastName("Reviewer")
                .build();
    }

    public User getReviewed() {
        return User.builder()
                .id(3L)
                .username("reviewed")
                .password("password")
                .email("reviewed@gmail.com")
                .firstName("Reviewed")
                .lastName("Reviewed")
                .build();
    }

}
