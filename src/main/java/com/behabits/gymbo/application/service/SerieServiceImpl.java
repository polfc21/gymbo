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
    public void deleteSerie(Long id) {
        Serie serie = this.serieDao.findSerieById(id);
        this.authorityService.checkLoggedUserHasPermissions(serie);
        this.serieDao.deleteSerie(serie);
    }
}
