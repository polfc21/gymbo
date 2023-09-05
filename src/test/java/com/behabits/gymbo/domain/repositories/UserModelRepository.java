package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
public class UserModelRepository {

    public User getUser() {
        return User.builder()
                .id(1L)
                .username("pol")
                .firstName("Pol")
                .lastName("Farreny Capdevila")
                .email("polfarreny@gmail.com")
                .sports(Set.of(Sport.FOOTBALL))
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

    public User getFootballUser() {
        return User.builder()
                .id(4L)
                .username("football")
                .password("password")
                .email("football@football.com")
                .firstName("Football")
                .lastName("Football")
                .sports(Set.of(Sport.FOOTBALL))
                .build();
    }

}
