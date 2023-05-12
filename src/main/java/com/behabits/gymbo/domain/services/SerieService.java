package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Serie;

public interface SerieService {
    Serie findSerieById(Long id);
    void deleteSerie(Long id);
}
