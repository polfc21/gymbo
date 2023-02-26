package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ExerciseEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaExerciseDao implements ExerciseDao {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseEntityMapper mapper;

    @Override
    public Exercise findExerciseById(Long id) {
        ExerciseEntity entity = this.exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exercise with " + id + " not found"));
        return this.mapper.toDomain(entity);
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        ExerciseEntity entity = this.mapper.toEntity(exercise);
        entity = this.exerciseRepository.save(entity);
        return this.mapper.toDomain(entity);
    }
}
