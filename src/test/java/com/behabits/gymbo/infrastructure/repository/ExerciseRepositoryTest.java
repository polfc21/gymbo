package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.ExerciseEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenExerciseWithTrainingIdWhenFindAllByTrainingIdThenReturnExerciseListSizeIs1() {
        TrainingEntity training = new TrainingEntityRepository().getLegTraining();
        training.setId(null);
        ExerciseEntity exercise = new ExerciseEntityRepository().getSquatExercise();
        exercise.setId(null);
        Long trainingId = entityManager.persistAndGetId(training, Long.class);
        exercise.setTraining(training);
        entityManager.persist(exercise);

        List<ExerciseEntity> exerciseList = exerciseRepository.findAllByTrainingId(trainingId);

        assertThat(exerciseList.size(), is(1));
    }

    @Test
    void givenExerciseWithoutTrainingIdWhenFindAllByTrainingIdNullThenReturnExerciseListSizeIs1() {
        ExerciseEntity exercise = new ExerciseEntityRepository().getSquatExercise();
        exercise.setId(null);
        entityManager.persist(exercise);

        List<ExerciseEntity> exerciseList = exerciseRepository.findAllByTrainingId(null);

        assertThat(exerciseList.size(), is(1));
    }
}
