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

}
