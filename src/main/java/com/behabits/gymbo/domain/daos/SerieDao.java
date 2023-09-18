package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Serie;

import java.util.List;

public interface SerieDao {
    Serie findSerieById(Long id);
    Serie updateSerie(Long id, Serie serie);
    void deleteSerie(Serie serie);
    Serie createSerie(Long exerciseId, Serie serie);
}
