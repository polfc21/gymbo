package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.LocationEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.LocationEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();
    private final LocationEntity location = new LocationEntityRepository().getBarcelona();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.location.setId(null);
        this.location.setPlayer(this.player);
        this.entityManager.persist(this.location);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.location);
        this.entityManager.remove(this.player);
    }

    @Test
    void givenLocationOfUserWhenFindByIdAndPlayerIdThenReturnLocation() {
        LocationEntity location = this.locationRepository.findByIdAndPlayerId(this.location.getId(), this.player.getId());

        assertThat(location, is(this.location));
    }

    @Test
    void givenLocationOfUserWhenFindByIdAndPlayerIdThenReturnNull() {
        LocationEntity location = this.locationRepository.findByIdAndPlayerId(this.location.getId() + 1, this.player.getId());

        assertNull(location);
    }
}
