package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.ExerciseEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();
    private final TrainingEntity training = new TrainingEntityRepository().getLegTraining();
    private final ExerciseEntity exercise = new ExerciseEntityRepository().getSquatExercise();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.training.setId(null);
        this.training.setPlayer(this.player);
        this.entityManager.persist(this.training);
        this.exercise.setId(null);
        this.exercise.setPlayer(this.player);
        this.exercise.setTraining(this.training);
        this.entityManager.persist(this.exercise);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.exercise);
        this.entityManager.remove(this.training);
        this.entityManager.remove(this.player);
    }


    @Test
    void givenExerciseWithTrainingIdAndPlayerIdWhenFindAllByTrainingIdAndPlayerIdThenReturnExerciseListSizeIs1() {
        List<ExerciseEntity> exerciseList = this.exerciseRepository.findAllByTrainingIdAndPlayerId(this.training.getId(), this.player.getId());

        assertThat(exerciseList.size(), is(1));
    }

    @Test
    void givenExerciseWithoutTrainingIdAndWithPlayerIdWhenFindAllByTrainingIdNullThenReturnExerciseListSizeIs1() {
        this.exercise.setTraining(null);
        this.entityManager.persist(this.exercise);

        List<ExerciseEntity> exerciseList = this.exerciseRepository.findAllByTrainingIdAndPlayerId(null, this.player.getId());

        assertThat(exerciseList.size(), is(1));
    }

    @Test
    void givenExerciseWithoutTrainingIdAndOtherPlayerIdWhenFindAllByTrainingIdNullThenReturnExerciseListSizeIs0() {
        this.exercise.setTraining(null);
        this.entityManager.persist(this.exercise);

        List<ExerciseEntity> exerciseList = this.exerciseRepository.findAllByTrainingIdAndPlayerId(null, this.player.getId() + 1);

        assertThat(exerciseList.size(), is(0));
    }

    @Test
    void givenExerciseOfUserWhenFindByIdAndPlayerIdThenReturnExercise() {
        ExerciseEntity exerciseEntity = this.exerciseRepository.findByIdAndPlayerId(this.exercise.getId(), this.player.getId());

        assertThat(exerciseEntity, is(this.exercise));
    }

    @Test
    void givenExerciseOfOtherUserWhenFindByIdAndPlayerIdThenReturnNull() {
        ExerciseEntity exerciseEntity = this.exerciseRepository.findByIdAndPlayerId(this.exercise.getId(), this.player.getId() + 1);

        assertNull(exerciseEntity);
    }
}
