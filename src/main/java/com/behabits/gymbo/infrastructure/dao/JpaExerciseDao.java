package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ExerciseDao;
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
    public Exercise saveExercise(Exercise exercise) {
        ExerciseEntity entityToSave = this.mapper.toEntity(exercise);
        ExerciseEntity entitySaved = this.exerciseRepository.save(entityToSave);
        return this.mapper.toDomain(entitySaved);
    }

    @Override
    public List<Exercise> findExercisesByTrainingIdAndUserId(Long trainingId, Long userId) {
        return this.exerciseRepository.findAllByTrainingIdAndPlayerId(trainingId, userId)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        this.exerciseRepository.deleteById(exercise.getId());
    }

}
