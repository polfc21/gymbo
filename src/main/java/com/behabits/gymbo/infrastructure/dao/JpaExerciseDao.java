package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ExerciseEntityMapper;
import com.behabits.gymbo.infrastructure.repository.mapper.SerieEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaExerciseDao implements ExerciseDao {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseEntityMapper mapper;
    private final SerieRepository serieRepository;
    private final SerieEntityMapper serieMapper;

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

    @Override
    public List<Exercise> findExercisesByTrainingIdAndUserId(Long trainingId, Long userId) {
        List<ExerciseEntity> entities = this.exerciseRepository.findAllByTrainingIdAndPlayerId(trainingId, userId);
        return this.mapper.toDomain(entities);
    }

    @Override
    public List<Serie> findSeriesByExerciseId(Long exerciseId) {
        ExerciseEntity exerciseEntity = this.exerciseRepository.getReferenceById(exerciseId);
        return this.serieMapper.toDomain(exerciseEntity.getSeries());
    }

    @Override
    public Serie createSerie(Long exerciseId, Serie serie) {
        ExerciseEntity exerciseEntity = this.exerciseRepository.getReferenceById(exerciseId);
        SerieEntity serieEntity = this.serieMapper.toEntity(serie);
        serieEntity.setExercise(exerciseEntity);
        serieEntity = this.serieRepository.save(serieEntity);
        return this.serieMapper.toDomain(serieEntity);
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        this.exerciseRepository.deleteById(exercise.getId());
    }
}
