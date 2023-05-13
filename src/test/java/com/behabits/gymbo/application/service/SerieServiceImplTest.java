package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.SerieDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@ExtendWith(MockitoExtension.class)
class SerieServiceImplTest {

    @InjectMocks
    private SerieServiceImpl serieService;

    @Mock
    private SerieDao serieDao;

    @Mock
    private AuthorityService authorityService;

    private final Serie squatSerie = new SerieModelRepository().getSquatSerie();

    @Test
    void givenNonExistentSerieWhenFindSerieByIdThenThrowNotFoundException() {
        doThrow(NotFoundException.class).when(this.serieDao).findSerieById(this.squatSerie.getId());

        assertThrows(NotFoundException.class, () -> this.serieService.findSerieById(this.squatSerie.getId()));
    }

    @Test
    void givenExistentSerieAndUserHasNotPermissionsWhenFindSerieByIdThenThrowPermissionsException() {
        when(this.serieDao.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        assertThrows(PermissionsException.class, () -> this.serieService.findSerieById(this.squatSerie.getId()));
    }

    @Test
    void givenExistentSeriesAndUserHasPermissionsWhenFindSerieByIdThenReturnSerie() {
        when(this.serieDao.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        try {
            this.serieService.findSerieById(this.squatSerie.getId());
        } catch (Exception e) {
            fail("Should not throw any exception");
        }
    }

    @Test
    void givenNonExistentSerieWhenDeleteSerieThenThrowNotFoundException() {
        doThrow(NotFoundException.class).when(this.serieDao).findSerieById(this.squatSerie.getId());

        assertThrows(NotFoundException.class, () -> this.serieService.deleteSerie(this.squatSerie.getId()));
    }

    @Test
    void givenExistentSerieAndUserHasNotPermissionsWhenDeleteSerieThenThrowPermissionsException() {
        when(this.serieDao.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        assertThrows(PermissionsException.class, () -> this.serieService.deleteSerie(this.squatSerie.getId()));
    }

    @Test
    void givenExistentSerieAndUserHasPermissionsWhenDeleteSerieThenDeleteSerie() {
        when(this.serieDao.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);
        doNothing().when(this.serieDao).deleteSerie(this.squatSerie);

        try {
            this.serieService.deleteSerie(this.squatSerie.getId());
        } catch (Exception e) {
            fail("Should not throw any exception");
        }
    }

    @Test
    void givenExistentSerieAndUserHasNotPermissionsWhenUpdateSerieThenThrowPermissionsException() {
        when(this.serieDao.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        assertThrows(PermissionsException.class, () -> this.serieService.updateSerie(this.squatSerie.getId(), this.squatSerie));
    }

    @Test
    void givenExistentSerieAndUserHasPermissionsWhenUpdateSerieThenUpdateSerie() {
        when(this.serieDao.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);
        when(this.serieDao.updateSerie(this.squatSerie.getId(), this.squatSerie)).thenReturn(this.squatSerie);

        assertThat(this.serieService.updateSerie(this.squatSerie.getId(), this.squatSerie), is(this.squatSerie));
    }

    @Test
    void givenNonExistentSerieWhenUpdateSerieThenThrowNotFoundException() {
        doThrow(NotFoundException.class).when(this.serieDao).findSerieById(this.squatSerie.getId());

        assertThrows(NotFoundException.class, () -> this.serieService.updateSerie(this.squatSerie.getId(), this.squatSerie));
    }
}
