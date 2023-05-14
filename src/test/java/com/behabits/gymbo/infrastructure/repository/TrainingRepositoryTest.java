package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();
    private final TrainingEntity training = new TrainingEntityRepository().getLegTraining();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.training.setId(null);
        this.training.setPlayer(this.player);
        this.entityManager.persist(this.training);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.training);
        this.entityManager.remove(this.player);
    }

    @Test
    void givenTrainingOfActualMonthAndActualYearAndUserIdIsPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs1() {
        Integer actualMonth = LocalDateTime.now().getMonthValue();
        Integer actualYear = LocalDateTime.now().getYear();

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth, actualYear, this.player.getId());

        assertThat(trainingList.size(), is(1));
    }

    @Test
    void givenTrainingOfNextMonthAndNextYearAndUserIdIsPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        int nextMonth = LocalDateTime.now().getMonthValue() + 1;
        int nextYear = LocalDateTime.now().getYear() + 1;

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(nextMonth, nextYear, this.player.getId());

        assertThat(trainingList.size(), is(0));
    }

    @Test
    void givenTrainingOfNextMonthAndNextYearAndUserIdIsNotPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        int nextMonth = LocalDateTime.now().getMonthValue() + 1;
        int nextYear = LocalDateTime.now().getYear() + 1;

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(nextMonth, nextYear, this.player.getId());

        assertThat(trainingList.size(), is(0));
    }

    @Test
    void givenTrainingOfActualMonthAndActualYearAndUserIdIsPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        Integer actualMonth = LocalDateTime.now().getMonthValue();
        Integer actualYear = LocalDateTime.now().getYear();

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth, actualYear, this.player.getId() + 1);

        assertThat(trainingList.size(), is(0));
    }

    @Test
    void givenTrainingOfUserWhenFindByIdAndPlayerIdThenReturnTraining() {
        TrainingEntity trainingEntity = this.trainingRepository.findByIdAndPlayerId(this.training.getId(), this.player.getId());

        assertThat(trainingEntity, is(this.training));
    }

    @Test
    void givenTrainingOfUserWhenFindByAnotherIdAndPlayerIdThenReturnNull() {
        TrainingEntity trainingEntity = this.trainingRepository.findByIdAndPlayerId(this.training.getId() + 1, this.player.getId() + 1);

        assertNull(trainingEntity);
    }
}
