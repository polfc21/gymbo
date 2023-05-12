package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.SerieDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

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
}
