package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Serie;

public interface SerieDao {
    Serie findSerieById(Long id);
    void deleteSerie(Serie serie);
}
