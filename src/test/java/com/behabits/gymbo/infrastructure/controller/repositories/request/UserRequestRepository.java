package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;

import java.util.Set;

public class UserRequestRepository {

    public UserRequest getCorrectUserRequest() {
        return UserRequest.builder()
                .username("pol")
                .email("polfarreny@gmail.com")
                .firstName("Pol")
                .lastName("Farreny Capdevila")
                .password("12345678")
                .matchingPassword("12345678")
                .sports(Set.of("FOOTBALL"))
                .build();
    }

    public UserRequest getIncorrectUserRequest() {
        return UserRequest.builder()
                .username("")
                .email("")
                .firstName("")
                .lastName("")
                .password("")
                .matchingPassword("")
                .sports(Set.of(""))
                .build();
    }

}
