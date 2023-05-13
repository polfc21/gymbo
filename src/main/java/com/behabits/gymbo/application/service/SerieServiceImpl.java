package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.SerieDao;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {

    private final SerieDao serieDao;
    private final AuthorityService authorityService;

    @Override
    public Serie findSerieById(Long id) {
        Serie serie = this.serieDao.findSerieById(id);
        this.authorityService.checkLoggedUserHasPermissions(serie);
        return serie;
    }

    @Override
    public Serie updateSerie(Long id, Serie serie) {
        Serie serieToUpdate = this.findSerieById(id);
        this.authorityService.checkLoggedUserHasPermissions(serieToUpdate);
        return this.serieDao.updateSerie(id, serie);
    }

    @Override
    public void deleteSerie(Long id) {
        Serie serie = this.findSerieById(id);
        this.serieDao.deleteSerie(serie);
    }
}
