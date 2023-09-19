package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ExerciseEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaExerciseDao implements ExerciseDao {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseEntityMapper mapper;

    @Override
    public Exercise findExerciseById(Long id) {
        ExerciseEntity entity = this.exerciseRepository.findById(id)
                .orElse(null);
        return entity != null ? this.mapper.toDomain(entity) : null;
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        ExerciseEntity entity = this.mapper.toEntity(exercise);
        entity = this.exerciseRepository.save(entity);
        return this.mapper.toDomain(entity);
    }

    @Override
    public List<Exercise> findExercisesByTrainingIdAndUserId(Long trainingId, Long userId) {
        List<ExerciseEntity> entities = this.exerciseRepository.findAllByTrainingIdAndPlayerId(trainingId, userId);
        return this.mapper.toDomain(entities);
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        this.exerciseRepository.deleteById(exercise.getId());
    }

    @Override
    public Exercise updateExercise(Long id, Exercise exercise) {
        ExerciseEntity exerciseEntity = this.exerciseRepository.getReferenceById(id);
        exerciseEntity.setName(exercise.getName());
        exerciseEntity = this.exerciseRepository.save(exerciseEntity);
        return this.mapper.toDomain(exerciseEntity);
    }
}
