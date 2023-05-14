package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.ExerciseEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.SerieEntityRepository;
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
class SerieRepositoryTest {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();
    private final ExerciseEntity exercise = new ExerciseEntityRepository().getSquatExercise();
    private final SerieEntity serie = new SerieEntityRepository().getSquatSerie();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.exercise.setPlayer(this.player);
        this.exercise.setId(null);
        this.entityManager.persist(this.exercise);
        this.serie.setExercise(this.exercise);
        this.serie.setId(null);
        this.entityManager.persist(this.serie);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.serie);
        this.entityManager.remove(this.exercise);
        this.entityManager.remove(this.player);
    }

    @Test
    void givenIdAndPlayerIdAreAllPresentWhenFindByIdAndPlayerIdThenReturnSerie() {
        SerieEntity serieFound = this.serieRepository.findByIdAndPlayerId(this.serie.getId(), this.player.getId());

        assertThat(serieFound, is(this.serie));
    }

    @Test
    void givenIdAndPlayerIdAndPlayerIdIsNotPresentWhenFindByIdAndPlayerIdThenReturnNull() {
        SerieEntity serieFound = this.serieRepository.findByIdAndPlayerId(this.serie.getId(), this.player.getId() + 1);

        assertNull(serieFound);
    }

    @Test
    void givenIdAndPlayerIdAndIdIsNotPresentWhenFindByIdAndPlayerIdThenReturnNull() {
        SerieEntity serieFound = this.serieRepository.findByIdAndPlayerId(this.serie.getId() + 1, this.player.getId());

        assertNull(serieFound);
    }

    @Test
    void givenIdAndPlayerIdAndIdAndPlayerIdAreNotPresentWhenFindByIdAndPlayerIdThenReturnNull() {
        SerieEntity serieFound = this.serieRepository.findByIdAndPlayerId(this.serie.getId() + 1, this.player.getId() + 1);

        assertNull(serieFound);
    }
}
