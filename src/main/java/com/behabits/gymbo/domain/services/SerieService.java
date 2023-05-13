package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Serie;

public interface SerieService {
    Serie findSerieById(Long id);
    Serie updateSerie(Long id, Serie serie);
    void deleteSerie(Long id);
}
