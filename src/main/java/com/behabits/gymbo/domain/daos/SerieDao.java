package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Serie;

public interface SerieDao {
    Serie saveSerie(Serie serie);
    Serie findSerieById(Long id);
    Serie updateSerie(Long id, Serie serie);
    void deleteSerie(Serie serie);
}
