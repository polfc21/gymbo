package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class SportApiMapperTest {

    @Autowired
    private SportApiMapper sportApiMapper;

    @Test
    void givenSportRequestWhenMapToDomainThenReturnSport() {
        String sportRequest = "football";

        Sport sport = this.sportApiMapper.toDomain(sportRequest);

        assertThat(sport, is(Sport.FOOTBALL));
    }

    @Test
    void givenSportRequestsWhenMapToDomainThenReturnSports() {
        Set<String> sportRequests = Set.of("football", "basketball");

        Set<Sport> sports = this.sportApiMapper.toDomain(sportRequests);

        assertThat(sports.size(), is(2));
        assertThat(sports.contains(Sport.FOOTBALL), is(true));
        assertThat(sports.contains(Sport.BASKETBALL), is(true));
    }
}
