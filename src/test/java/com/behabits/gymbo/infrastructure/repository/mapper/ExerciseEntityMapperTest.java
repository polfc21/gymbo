package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.ExerciseEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class ExerciseEntityMapperTest {

    @Autowired
    private ExerciseEntityMapper mapper;

    private final ExerciseEntityRepository exerciseEntityRepository = new ExerciseEntityRepository();

    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    @Test
    void givenSquatExerciseWhenMapToEntityThenReturnSquatExerciseEntity() {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExercise();

        ExerciseEntity squatExerciseEntity = this.mapper.toEntity(squatExercise);

        assertThat(squatExerciseEntity.getId(), is(squatExercise.getId()));
        assertThat(squatExerciseEntity.getName(), is(squatExercise.getName()));
        assertThat(squatExerciseEntity.getSeries(), is(squatExercise.getSeries()));
    }

    @Test
    void givenSquatExerciseEntityWhenMapToDomainThenReturnSquatExercise() {
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExercise();

        Exercise squatExercise = this.mapper.toDomain(squatExerciseEntity);

        assertThat(squatExercise.getId(), is(squatExerciseEntity.getId()));
        assertThat(squatExercise.getName(), is(squatExerciseEntity.getName()));
        assertThat(squatExercise.getSeries(), is(squatExerciseEntity.getSeries()));
    }

    @Test
    void givenSquatExerciseWithSerieWhenMapToEntityThenReturnSquatExerciseEntityWithSerie() {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        ExerciseEntity squatExerciseEntity = this.mapper.toEntity(squatExercise);

        assertThat(squatExerciseEntity.getId(), is(squatExercise.getId()));
        assertThat(squatExerciseEntity.getName(), is(squatExercise.getName()));

        assertThat(squatExerciseEntity.getSeries().get(0).getId(),
                is(squatExercise.getSeries().get(0).getId()));
        assertThat(squatExerciseEntity.getSeries().get(0).getNumber(),
                is(squatExercise.getSeries().get(0).getNumber()));
        assertThat(squatExerciseEntity.getSeries().get(0).getRepetitions(),
                is(squatExercise.getSeries().get(0).getRepetitions()));
        assertThat(squatExerciseEntity.getSeries().get(0).getWeight(),
                is(squatExercise.getSeries().get(0).getWeight()));
    }

    @Test
    void givenSquatExerciseEntityWithSerieWhenMapToDomainThenReturnSquatExerciseWithSerie() {
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExerciseWithSeries();

        Exercise squatExercise = this.mapper.toDomain(squatExerciseEntity);

        assertThat(squatExercise.getId(), is(squatExerciseEntity.getId()));
        assertThat(squatExercise.getName(), is(squatExerciseEntity.getName()));

        assertThat(squatExercise.getSeries().get(0).getId(),
                is(squatExerciseEntity.getSeries().get(0).getId()));
        assertThat(squatExercise.getSeries().get(0).getNumber(),
                is(squatExerciseEntity.getSeries().get(0).getNumber()));
        assertThat(squatExercise.getSeries().get(0).getRepetitions(),
                is(squatExerciseEntity.getSeries().get(0).getRepetitions()));
        assertThat(squatExercise.getSeries().get(0).getWeight(),
                is(squatExerciseEntity.getSeries().get(0).getWeight()));
    }
}
