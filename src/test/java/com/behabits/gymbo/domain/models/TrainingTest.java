package com.behabits.gymbo.domain.models;

import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class TrainingTest {

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();
    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    @Test
    void givenTrainingWithoutExercisesWhenAddExerciseThenTrainingHasExercise() {
        Training training = this.trainingModelRepository.getLegTraining();
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        training.addExercise(exercise);

        assertThat(training.getExercises(), hasItem(exercise));
    }

    @Test
    void givenTrainingWithExercisesWhenAddExerciseThenTrainingHasExercise() {
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        training.addExercise(exercise);

        assertThat(training.getExercises(), hasItem(exercise));
    }
}
