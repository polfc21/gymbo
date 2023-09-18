package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.SerieDao;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.SerieEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaSerieDao implements SerieDao {

    private final SerieRepository serieRepository;
    private final SerieEntityMapper mapper;
    private final ExerciseRepository exerciseRepository;

    @Override
    public Serie saveSerie(Serie serie) {
        SerieEntity entityToCreate = this.mapper.toEntity(serie);
        SerieEntity createdEntity = this.serieRepository.save(entityToCreate);
        return this.mapper.toDomain(createdEntity);
    }

    @Override
    public Serie findSerieById(Long id) {
        SerieEntity serieEntity = this.serieRepository.findById(id)
                .orElse(null);
        return serieEntity != null ? this.mapper.toDomain(serieEntity) : null;
    }

    @Override
    public Serie updateSerie(Long id, Serie serie) {
        SerieEntity serieEntity = this.serieRepository.getReferenceById(id);
        serieEntity.setNumber(serie.getNumber());
        serieEntity.setRepetitions(serie.getRepetitions());
        serieEntity.setWeight(serie.getWeight());
        serieEntity = this.serieRepository.save(serieEntity);
        return this.mapper.toDomain(serieEntity);
    }

    @Override
    public void deleteSerie(Serie serie) {
        this.serieRepository.deleteById(serie.getId());
    }

    @Override
    public Serie createSerie(Long exerciseId, Serie serie) {
        ExerciseEntity exerciseEntity = this.exerciseRepository.getReferenceById(exerciseId);
        SerieEntity serieEntity = this.mapper.toEntity(serie);
        serieEntity.setExercise(exerciseEntity);
        serieEntity = this.serieRepository.save(serieEntity);
        return this.mapper.toDomain(serieEntity);
    }

}
