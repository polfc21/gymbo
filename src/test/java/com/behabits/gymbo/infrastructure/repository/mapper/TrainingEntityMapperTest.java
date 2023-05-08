package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class TrainingEntityMapperTest {

    @Autowired
    private TrainingEntityMapper mapper;

    private final TrainingEntityRepository trainingEntityRepository = new TrainingEntityRepository();

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();

    @Test
    void givenLegTrainingWhenMapToEntityThenReturnLegTrainingEntity() {
        Training legTraining = this.trainingModelRepository.getLegTraining();

        TrainingEntity legTrainingEntity = this.mapper.toEntity(legTraining);

        assertThat(legTrainingEntity.getId(), is(legTraining.getId()));
        assertThat(legTrainingEntity.getName(), is(legTraining.getName()));
        assertThat(legTrainingEntity.getTrainingDate(), is(legTraining.getTrainingDate()));
        assertThat(legTrainingEntity.getExercises(), is(legTraining.getExercises()));
    }

    @Test
    void givenLegTrainingEntityWhenMapToDomainThenReturnLegTraining() {
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTraining();

        Training legTraining = this.mapper.toDomain(legTrainingEntity);

        assertThat(legTraining.getId(), is(legTrainingEntity.getId()));
        assertThat(legTraining.getName(), is(legTrainingEntity.getName()));
        assertThat(legTraining.getTrainingDate(), is(legTrainingEntity.getTrainingDate()));
        assertThat(legTraining.getExercises(), is(legTrainingEntity.getExercises()));
    }

    @Test
    void givenLegTrainingWithSquatExerciseWhenMapToEntityThenReturnLegTrainingEntityWithSquatExercise() {
        Training legTraining = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        TrainingEntity legTrainingEntity = this.mapper.toEntity(legTraining);

        assertThat(legTrainingEntity.getId(), is(legTraining.getId()));
        assertThat(legTrainingEntity.getName(), is(legTraining.getName()));
        assertThat(legTrainingEntity.getTrainingDate(), is(legTraining.getTrainingDate()));

        assertThat(legTrainingEntity.getExercises().get(0).getId(),
                is(legTraining.getExercises().get(0).getId()));
        assertThat(legTrainingEntity.getExercises().get(0).getName(),
                is(legTraining.getExercises().get(0).getName()));

        assertThat(legTrainingEntity.getExercises().get(0).getSeries().get(0).getId(),
                is(legTraining.getExercises().get(0).getSeries().get(0).getId()));
        assertThat(legTrainingEntity.getExercises().get(0).getSeries().get(0).getNumber(),
                is(legTraining.getExercises().get(0).getSeries().get(0).getNumber()));
        assertThat(legTrainingEntity.getExercises().get(0).getSeries().get(0).getRepetitions(),
                is(legTraining.getExercises().get(0).getSeries().get(0).getRepetitions()));
        assertThat(legTrainingEntity.getExercises().get(0).getSeries().get(0).getWeight(),
                is(legTraining.getExercises().get(0).getSeries().get(0).getWeight()));
    }

    @Test
    void givenLegTrainingEntityWithSquatExerciseWhenMapToDomainThenReturnLegTrainingWithSquatExercise() {
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTrainingWithSquatExerciseWithSeries();

        Training legTraining = this.mapper.toDomain(legTrainingEntity);

        assertThat(legTraining.getId(), is(legTrainingEntity.getId()));
        assertThat(legTraining.getName(), is(legTrainingEntity.getName()));
        assertThat(legTraining.getTrainingDate(), is(legTrainingEntity.getTrainingDate()));

        assertThat(legTraining.getExercises().get(0).getId(),
                is(legTrainingEntity.getExercises().get(0).getId()));
        assertThat(legTraining.getExercises().get(0).getName(),
                is(legTrainingEntity.getExercises().get(0).getName()));

        assertThat(legTraining.getExercises().get(0).getSeries().get(0).getId(),
                is(legTrainingEntity.getExercises().get(0).getSeries().get(0).getId()));
        assertThat(legTraining.getExercises().get(0).getSeries().get(0).getNumber(),
                is(legTrainingEntity.getExercises().get(0).getSeries().get(0).getNumber()));
        assertThat(legTraining.getExercises().get(0).getSeries().get(0).getRepetitions(),
                is(legTrainingEntity.getExercises().get(0).getSeries().get(0).getRepetitions()));
        assertThat(legTraining.getExercises().get(0).getSeries().get(0).getWeight(),
                is(legTrainingEntity.getExercises().get(0).getSeries().get(0).getWeight()));
    }

    @Test
    void givenLegTrainingOfUserWhenMapToEntityThenReturnLegTrainingEntityOfUser() {
        Training legTraining = this.trainingModelRepository.getLegTraining();
        legTraining.setUser(new UserModelRepository().getUser());

        TrainingEntity legTrainingEntity = this.mapper.toEntity(legTraining);

        assertThat(legTrainingEntity.getId(), is(legTraining.getId()));
        assertThat(legTrainingEntity.getName(), is(legTraining.getName()));
        assertThat(legTrainingEntity.getTrainingDate(), is(legTraining.getTrainingDate()));
        assertThat(legTrainingEntity.getExercises(), is(legTraining.getExercises()));
        assertThat(legTrainingEntity.getPlayer().getId(), is(legTraining.getUser().getId()));
    }

    @Test
    void givenLegTrainingEntityOfUserWhenMapToDomainThenReturnLegTrainingOfUser() {
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTraining();
        legTrainingEntity.setPlayer(new UserEntityRepository().getUser());

        Training legTraining = this.mapper.toDomain(legTrainingEntity);

        assertThat(legTraining.getId(), is(legTrainingEntity.getId()));
        assertThat(legTraining.getName(), is(legTrainingEntity.getName()));
        assertThat(legTraining.getTrainingDate(), is(legTrainingEntity.getTrainingDate()));
        assertThat(legTraining.getExercises(), is(legTrainingEntity.getExercises()));
        assertThat(legTraining.getUser().getId(), is(legTrainingEntity.getPlayer().getId()));
    }
}
