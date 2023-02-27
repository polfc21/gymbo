package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenTrainingOfActualMonthAndActualYearWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs1() {
        Integer actualMonth = LocalDateTime.now().getMonthValue();
        Integer actualYear = LocalDateTime.now().getYear();
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        entityManager.persist(training);

        List<TrainingEntity> trainingList = trainingRepository.findAllByMonthAndYear(actualMonth, actualYear);

        assert trainingList.size() == 1;
    }

    @Test
    void givenTrainingOfNextMonthAndNextYearWhenFindAllByMonthAndYearThenReturnTrainingListSizeIs0() {
        int actualMonth = LocalDateTime.now().getMonthValue();
        int actualYear = LocalDateTime.now().getYear();
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        entityManager.persist(training);

        List<TrainingEntity> trainingList = trainingRepository.findAllByMonthAndYear(actualMonth + 1, actualYear + 1);

        assert trainingList.size() == 0;
    }
}
