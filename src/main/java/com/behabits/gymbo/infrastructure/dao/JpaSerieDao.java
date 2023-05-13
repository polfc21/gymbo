package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.SerieDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.SerieEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaSerieDao implements SerieDao {

    private final SerieRepository serieRepository;
    private final SerieEntityMapper mapper;

    @Override
    public Serie findSerieById(Long id) {
        SerieEntity serieEntity = this.serieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serie with" + id + " not found"));
        return this.mapper.toDomain(serieEntity);
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

}
