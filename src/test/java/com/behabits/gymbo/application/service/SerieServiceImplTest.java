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
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.serieDao).findSerieById(nonExistentId);

        assertThrows(NotFoundException.class, () -> this.serieService.findSerieById(nonExistentId));
    }

    @Test
    void givenExistentSerieAndUserHasNotPermissionsWhenFindSerieByIdThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.serieDao.findSerieById(existentId)).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        assertThrows(PermissionsException.class, () -> this.serieService.findSerieById(existentId));
    }

    @Test
    void givenExistentSeriesAndUserHasPermissionsWhenFindSerieByIdThenReturnSerie() {
        Long existentId = 1L;

        when(this.serieDao.findSerieById(existentId)).thenReturn(this.squatSerie);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        try {
            this.serieService.findSerieById(existentId);
        } catch (Exception e) {
            fail("Should not throw any exception");
        }
    }

    @Test
    void givenNonExistentSerieWhenDeleteSerieThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.serieDao).findSerieById(nonExistentId);

        assertThrows(NotFoundException.class, () -> this.serieService.deleteSerie(nonExistentId));
    }

    @Test
    void givenExistentSerieAndUserHasNotPermissionsWhenDeleteSerieThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.serieDao.findSerieById(existentId)).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        assertThrows(PermissionsException.class, () -> this.serieService.deleteSerie(existentId));
    }

    @Test
    void givenExistentSerieAndUserHasPermissionsWhenDeleteSerieThenDeleteSerie() {
        Long existentId = 1L;

        when(this.serieDao.findSerieById(existentId)).thenReturn(this.squatSerie);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);
        doNothing().when(this.serieDao).deleteSerie(this.squatSerie);

        try {
            this.serieService.deleteSerie(existentId);
        } catch (Exception e) {
            fail("Should not throw any exception");
        }
    }

    @Test
    void givenExistentSerieAndUserHasNotPermissionsWhenUpdateSerieThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.serieDao.findSerieById(existentId)).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);

        assertThrows(PermissionsException.class, () -> this.serieService.updateSerie(existentId, this.squatSerie));
    }

    @Test
    void givenExistentSerieAndUserHasPermissionsWhenUpdateSerieThenUpdateSerie() {
        Long existentId = 1L;

        when(this.serieDao.findSerieById(existentId)).thenReturn(this.squatSerie);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.squatSerie);
        when(this.serieDao.updateSerie(existentId, this.squatSerie)).thenReturn(this.squatSerie);

        assertThat(this.serieService.updateSerie(existentId, this.squatSerie), is(this.squatSerie));
    }

    @Test
    void givenNonExistentSerieWhenUpdateSerieThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.serieDao).findSerieById(nonExistentId);

        assertThrows(NotFoundException.class, () -> this.serieService.updateSerie(nonExistentId, this.squatSerie));
    }
}
