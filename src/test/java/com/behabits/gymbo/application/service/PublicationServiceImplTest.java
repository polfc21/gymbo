package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PublicationServiceImplTest {

    @InjectMocks
    private PublicationServiceImpl publicationService;

    @Mock
    private PublicationDao publicationDao;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private FileService fileService;

    private final User loggedUser = new UserModelRepository().getUser();

    @Test
    void givenPublicationAndExistentFilesIdsWithPermissionsWhenCreatePublicationThenReturnPublication() {
        Publication publicationToCreate = mock(Publication.class);
        Publication publicationCreated = mock(Publication.class);
        File file = mock(File.class);
        Long existentFileId = 1L;

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.fileService.findFileById(existentFileId)).thenReturn(file);
        when(this.publicationDao.savePublication(publicationToCreate)).thenReturn(publicationCreated);

        assertThat(this.publicationService.createPublication(publicationToCreate, List.of(existentFileId)), is(publicationCreated));
        verify(publicationToCreate).setPostedBy(this.loggedUser);
        verify(publicationToCreate).setCreatedAt(any());
        verify(publicationCreated).setFiles(List.of(file));
    }

    @Test
    void givenPublicationAndExistentFilesIdsWithoutPermissionsWhenCreatePublicationThenThrowPermissionsException() {
        Publication publicationToCreate = mock(Publication.class);
        Long existentFileId = 1L;

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.fileService.findFileById(existentFileId)).thenThrow(PermissionsException.class);

        assertThrows(PermissionsException.class, () -> this.publicationService.createPublication(publicationToCreate, List.of(existentFileId)));
    }

    @Test
    void givenPublicationAndNonExistentFilesIdsWhenCreatePublicationThenThrowNotFoundException() {
        Publication publicationToCreate = mock(Publication.class);
        Long nonExistentFileId = 1L;

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.fileService.findFileById(nonExistentFileId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.publicationService.createPublication(publicationToCreate, List.of(nonExistentFileId)));
    }
}
