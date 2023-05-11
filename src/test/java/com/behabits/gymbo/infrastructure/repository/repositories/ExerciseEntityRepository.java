package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseEntityRepository {

    private final UserEntity player = new UserEntityRepository().getUser();

    public ExerciseEntity getSquatExercise() {
        return ExerciseEntity.builder()
                .id(1L)
                .name("Squat")
                .player(this.player)
                .build();
    }

    public ExerciseEntity getSquatExerciseWithSeries() {
        SerieEntityRepository serieEntityRepository = new SerieEntityRepository();
        return ExerciseEntity.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieEntityRepository.getSquatSerie()))
                .player(this.player)
                .build();
    }
}
