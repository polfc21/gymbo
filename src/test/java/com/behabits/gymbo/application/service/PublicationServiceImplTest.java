package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.FileService;
import com.behabits.gymbo.domain.services.LinkService;
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

    @Mock
    private LinkService linkService;

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

    @Test
    void givenPublicationWithExistentOwnLinksWhenCreatePublicationThenReturnPublication() {
        Publication publicationToCreate = mock(Publication.class);
        Publication publicationCreated = mock(Publication.class);
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(publicationToCreate.getLinks()).thenReturn(links);
        doNothing().when(this.linkService).setLinks(links);
        when(this.publicationDao.savePublication(publicationToCreate)).thenReturn(publicationCreated);

        assertThat(this.publicationService.createPublication(publicationToCreate, List.of()), is(publicationCreated));
        verify(publicationToCreate).setPostedBy(this.loggedUser);
        verify(publicationToCreate).setCreatedAt(any());
        verify(this.linkService).setLinks(links);
    }

    @Test
    void givenPublicationWithExistentNotOwnLinksWhenCreatePublicationThenThrowsPermissionsException() {
        Publication publicationToCreate = mock(Publication.class);
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(publicationToCreate.getLinks()).thenReturn(links);
        doThrow(PermissionsException.class).when(this.linkService).setLinks(links);

        assertThrows(PermissionsException.class, () -> this.publicationService.createPublication(publicationToCreate, List.of()));
    }

    @Test
    void givenPublicationWithNonExistentLinksWhenCreatePublicationThenThrowsNotFoundException() {
        Publication publicationToCreate = mock(Publication.class);
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(publicationToCreate.getLinks()).thenReturn(links);
        doThrow(NotFoundException.class).when(this.linkService).setLinks(links);

        assertThrows(NotFoundException.class, () -> this.publicationService.createPublication(publicationToCreate, List.of()));
    }

    @Test
    void givenPublicationWithIncorrectLinksWhenCreatePublicationThenThrowsIncorrectLinkException() {
        Publication publicationToCreate = mock(Publication.class);
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(publicationToCreate.getLinks()).thenReturn(links);
        doThrow(IncorrectLinkException.class).when(this.linkService).setLinks(links);

        assertThrows(IncorrectLinkException.class, () -> this.publicationService.createPublication(publicationToCreate, List.of()));
    }

    @Test
    void givenExistentPublicationWithPermissionsWhenUpdatePublicationThenReturnPublication() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);
        Publication publicationToUpdate = mock(Publication.class);
        Publication publicationUpdated = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(publicationToUpdate);
        when(this.publicationDao.savePublication(publicationToUpdate)).thenReturn(publicationUpdated);

        assertThat(this.publicationService.updatePublication(publicationId, publication), is(publicationUpdated));
        verify(publicationToUpdate).setDescription(publication.getDescription());
        verify(publicationToUpdate).setSport(publication.getSport());
        verify(publicationToUpdate).setUpdatedAt(any());
    }

    @Test
    void givenExistentPublicationWithoutPermissionsWhenUpdatePublicationThenThrowPermissionsException() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);
        Publication publicationToUpdate = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(publicationToUpdate);

        assertThrows(PermissionsException.class, () -> this.publicationService.updatePublication(publicationId, publication));
    }

    @Test
    void givenNonExistentPublicationWhenUpdatePublicationThenThrowNotFoundException() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.publicationService.updatePublication(publicationId, publication));
    }

}
