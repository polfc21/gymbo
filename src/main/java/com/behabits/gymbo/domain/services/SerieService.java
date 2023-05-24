package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Serie;

import java.util.List;

public interface SerieService {
    Serie findSerieById(Long id);
    Serie updateSerie(Long id, Serie serie);
    void deleteSerie(Long id);
    List<Serie> findSeriesByExerciseId(Long exerciseId);
    Serie createSerie(Long exerciseId, Serie serie);
}
