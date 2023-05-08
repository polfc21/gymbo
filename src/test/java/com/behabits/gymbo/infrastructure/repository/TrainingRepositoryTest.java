package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@DataJpaTest
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenTrainingOfActualMonthAndActualYearAndUserIdIsPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs1() {
        UserEntity user = new UserEntityRepository().getUser();
        user.setId(null);
        Integer actualMonth = LocalDateTime.now().getMonthValue();
        Integer actualYear = LocalDateTime.now().getYear();
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        Long userId = this.entityManager.persistAndGetId(user, Long.class);
        training.setPlayer(user);
        this.entityManager.persist(training);

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth, actualYear, userId);

        assertThat(trainingList.size(), is(1));
    }

    @Test
    void givenTrainingOfNextMonthAndNextYearAndUserIdIsPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        UserEntity user = new UserEntityRepository().getUser();
        user.setId(null);
        int actualMonth = LocalDateTime.now().getMonthValue();
        int actualYear = LocalDateTime.now().getYear();
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        Long userId = this.entityManager.persistAndGetId(user, Long.class);
        training.setPlayer(user);
        this.entityManager.persist(training);

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth + 1, actualYear + 1, userId);

        assertThat(trainingList.size(), is(0));
    }

    @Test
    void givenTrainingOfNextMonthAndNextYearAndUserIdIsNotPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        UserEntity user = new UserEntityRepository().getUser();
        user.setId(null);
        int actualMonth = LocalDateTime.now().getMonthValue();
        int actualYear = LocalDateTime.now().getYear();
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        Long userId = this.entityManager.persistAndGetId(user, Long.class);
        training.setPlayer(user);
        this.entityManager.persist(training);

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth + 1, actualYear + 1, userId + 1);

        assertThat(trainingList.size(), is(0));
    }

    @Test
    void givenTrainingOfActualMonthAndActualYearAndUserIdIsPresentWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        UserEntity user = new UserEntityRepository().getUser();
        user.setId(null);
        Integer actualMonth = LocalDateTime.now().getMonthValue();
        Integer actualYear = LocalDateTime.now().getYear();
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        Long userId = this.entityManager.persistAndGetId(user, Long.class);
        training.setPlayer(user);
        this.entityManager.persist(training);

        List<TrainingEntity> trainingList = this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth, actualYear, userId + 1);

        assertThat(trainingList.size(), is(0));
    }
}
