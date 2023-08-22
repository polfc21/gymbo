package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;

public class UserResponseRepository {

    public UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(1L)
                .username("pol")
                .firstName("Pol")
                .lastName("Farreny Capdevila")
                .email("polfarreny@gmail.com")
                .build();
    }

    public UserResponse getReviewer() {
        return UserResponse.builder()
                .id(2L)
                .username("reviewer")
                .firstName("Reviewer")
                .lastName("Reviewer")
                .email("reviewer@gmail.com")
                .build();
    }

    public UserResponse getReviewed() {
        return UserResponse.builder()
                .id(3L)
                .username("reviewed")
                .firstName("Reviewed")
                .lastName("Reviewed")
                .email("reviewed@gmail.com")
                .build();
    }

}
