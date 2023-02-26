package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseEntityRepository {

    public ExerciseEntity getSquatExercise() {
        return ExerciseEntity.builder()
                .id(1L)
                .name("Squat")
                .build();
    }

    public ExerciseEntity getSquatExerciseWithSeries() {
        SerieEntityRepository serieEntityRepository = new SerieEntityRepository();
        return ExerciseEntity.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieEntityRepository.getSquatSerie()))
                .build();
    }
}
