package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.LinkModelRepository;
import com.behabits.gymbo.domain.repositories.PublicationModelRepository;
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
    void givenExistentPublicationWithPermissionsWhenFindByPublicationByIdThenReturnPublication() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publication);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(publication);

        assertThat(this.publicationService.findPublicationById(publicationId), is(publication));
    }

    @Test
    void givenExistentPublicationWithoutPermissionsWhenFindByPublicationByIdThenThrowPermissionsException() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publication);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(publication);

        assertThrows(PermissionsException.class, () -> this.publicationService.findPublicationById(publicationId));
    }

    @Test
    void givenNonExistentPublicationWhenFindByPublicationByIdThenThrowNotFoundException() {
        Long publicationId = 1L;

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.publicationService.findPublicationById(publicationId));
    }

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

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.publicationService.updatePublication(publicationId, publication));
    }

    @Test
    void givenExistentLinkWithPermissionsWhenDeleteLinkThenDeleteLink() {
        Long linkId = 1L;

        doNothing().when(this.linkService).deleteLink(linkId);

        try {
            this.publicationService.deleteLink(linkId);
        } catch (Exception e) {
            assertThat(e, is(nullValue()));
        }
    }

    @Test
    void givenExistentLinkWithoutPermissionsWhenDeleteLinkThenThrowPermissionsException() {
        Long linkId = 1L;

        doThrow(PermissionsException.class).when(this.linkService).deleteLink(linkId);

        assertThrows(PermissionsException.class, () -> this.publicationService.deleteLink(linkId));
    }

    @Test
    void givenNonExistentLinkWhenDeleteLinkThenThrowNotFoundException() {
        Long linkId = 1L;

        doThrow(NotFoundException.class).when(this.linkService).deleteLink(linkId);

        assertThrows(NotFoundException.class, () -> this.publicationService.deleteLink(linkId));
    }

    @Test
    void givenExistentPublicationWithPermissionsWithOwnLinkWhenAddLinkThenReturnPublicationWithLinkAdded() {
        Long publicationId = 1L;
        Publication publicationToUpdate = mock(Publication.class);
        Publication publicationUpdated = mock(Publication.class);
        Link link = mock(Link.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(publicationToUpdate);
        doNothing().when(this.linkService).setLinks(List.of(link));
        doNothing().when(publicationToUpdate).addLink(link);
        when(this.publicationDao.savePublication(publicationToUpdate)).thenReturn(publicationUpdated);

        assertThat(this.publicationService.addLink(publicationId, link), is(publicationUpdated));
        verify(publicationToUpdate).addLink(link);
    }

    @Test
    void givenNonExistentPublicationWhenAddLinkThenThrowNotFoundException() {
        Long publicationId = 1L;
        Link link = mock(Link.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.publicationService.addLink(publicationId, link));
    }

    @Test
    void givenExistentPublicationWithoutPermissionsWhenAddLinkThenThrowPermissionsException() {
        Long publicationId = 1L;
        Publication publicationToUpdate = mock(Publication.class);
        Link link = mock(Link.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(publicationToUpdate);

        assertThrows(PermissionsException.class, () -> this.publicationService.addLink(publicationId, link));
    }

    @Test
    void givenNotExistentEntityWhenAddLinkThenThrowNotFoundException() {
        Long publicationId = 1L;
        Publication publicationToUpdate = mock(Publication.class);
        Link link = mock(Link.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doThrow(NotFoundException.class).when(this.linkService).setLinks(List.of(link));

        assertThrows(NotFoundException.class, () -> this.publicationService.addLink(publicationId, link));
    }

    @Test
    void givenExistentEntityWithoutPermissionsWhenAddLinkThenThrowPermissionsException() {
        Long publicationId = 1L;
        Publication publicationToUpdate = mock(Publication.class);
        Link link = mock(Link.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doThrow(PermissionsException.class).when(this.linkService).setLinks(List.of(link));

        assertThrows(PermissionsException.class, () -> this.publicationService.addLink(publicationId, link));
    }

    @Test
    void givenExistentEntityWithIncorrectLinkWhenAddLinkThenThrowIncorrectLinkException() {
        Long publicationId = 1L;
        Publication publicationToUpdate = mock(Publication.class);
        Link link = mock(Link.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doThrow(IncorrectLinkException.class).when(this.linkService).setLinks(List.of(link));

        assertThrows(IncorrectLinkException.class, () -> this.publicationService.addLink(publicationId, link));
    }

    @Test
    void givenPublicationWithSameLinkWhenAddLinkThenThrowIncorrectLinkException() {
        Long publicationId = 1L;
        Publication publicationToUpdate = mock(Publication.class);
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publicationToUpdate);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(publicationToUpdate);
        doNothing().when(this.linkService).setLinks(links);
        doThrow(IncorrectLinkException.class).when(publicationToUpdate).addLink(link);

        assertThrows(IncorrectLinkException.class, () -> this.publicationService.addLink(publicationId, link));
    }

    @Test
    void givenExistentPublicationWithPermissionsWithOwnLinkWhenAddLinkThenReturnPublicationWithLinkItem() {
        Publication publication = new PublicationModelRepository().getPublication();
        Link link = new LinkModelRepository().getLinkWithExercise();

        when(this.publicationDao.findPublicationById(publication.getId())).thenReturn(publication);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(publication);
        doNothing().when(this.linkService).setLinks(List.of(link));
        when(this.publicationDao.savePublication(publication)).thenReturn(publication);
        Publication publicationWithLink = this.publicationService.addLink(publication.getId(), link);

        assertThat(publicationWithLink.getLinks(), hasItem(link));
    }

    @Test
    void whenFindAllPublicationsThenReturnPublications() {
        List<Publication> publications = List.of(new PublicationModelRepository().getPublication());

        when(this.publicationDao.findAllPublications()).thenReturn(publications);

        assertThat(this.publicationService.findAllPublications(), is(publications));
    }

    @Test
    void givenExistentPublicationWithPermissionsWhenDeletePublicationThenDeletePublication() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publication);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(publication);

        try {
            this.publicationService.deletePublication(publicationId);
        } catch (Exception e) {
            assertThat(e, is(nullValue()));
        }
    }

    @Test
    void givenExistentPublicationWithoutPermissionsWhenDeletePublicationThenThrowPermissionsException() {
        Long publicationId = 1L;
        Publication publication = mock(Publication.class);

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(publication);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(publication);

        assertThrows(PermissionsException.class, () -> this.publicationService.deletePublication(publicationId));
    }

    @Test
    void givenNonExistentPublicationWhenDeletePublicationThenThrowNotFoundException() {
        Long publicationId = 1L;

        when(this.publicationDao.findPublicationById(publicationId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.publicationService.deletePublication(publicationId));
    }

}
