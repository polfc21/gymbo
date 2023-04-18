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
    public List<Exercise> findExercisesByTrainingId(Long trainingId) {
        List<ExerciseEntity> entities = this.exerciseRepository.findAllByTrainingId(trainingId);
        return this.mapper.toDomain(entities);
    }

    @Override
    public List<Serie> findSeriesByExerciseId(Long exerciseId) {
        Exercise exercise = this.findExerciseById(exerciseId);
        return exercise.getSeries();
    }

    @Override
    public Serie createSerie(Long exerciseId, Serie serie) {
        ExerciseEntity exerciseEntity = this.exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("Exercise with " + exerciseId + " not found"));
        SerieEntity serieEntity = this.serieMapper.toEntity(serie);
        serieEntity.setExercise(exerciseEntity);
        serieEntity = this.serieRepository.save(serieEntity);
        return this.serieMapper.toDomain(serieEntity);
    }
}
