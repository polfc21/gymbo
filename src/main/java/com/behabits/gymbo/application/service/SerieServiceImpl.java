package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.SerieDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.domain.services.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {

    private final SerieDao serieDao;
    private final AuthorityService authorityService;
    private final ExerciseService exerciseService;

    @Override
    public Serie findSerieById(Long id) {
        Serie serie = this.serieDao.findSerieById(id);
        if (serie == null) {
            throw new NotFoundException("Serie not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(serie);
        return serie;
    }

    @Override
    public Serie updateSerie(Long id, Serie serie) {
        Serie serieToUpdate = this.findSerieById(id);
        this.authorityService.checkLoggedUserHasPermissions(serieToUpdate);
        serieToUpdate.setNumber(serie.getNumber());
        serieToUpdate.setRepetitions(serie.getRepetitions());
        serieToUpdate.setWeight(serie.getWeight());
        return this.serieDao.saveSerie(serieToUpdate);
    }

    @Override
    public void deleteSerie(Long id) {
        Serie serie = this.findSerieById(id);
        this.serieDao.deleteSerie(serie);
    }

    @Override
    public List<Serie> findSeriesByExerciseId(Long exerciseId) {
        Exercise exercise = this.exerciseService.findExerciseById(exerciseId);
        this.authorityService.checkLoggedUserHasPermissions(exercise);
        return exercise.getSeries();
    }

    @Override
    public Serie createSerie(Long exerciseId, Serie serie) {
        Exercise exercise = this.exerciseService.findExerciseById(exerciseId);
        this.authorityService.checkLoggedUserHasPermissions(exercise);
        serie.setExercise(exercise);
        return this.serieDao.saveSerie(serie);
    }

}
