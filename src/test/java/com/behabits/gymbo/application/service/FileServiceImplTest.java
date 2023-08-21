package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.FileDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.FileModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileDao fileDao;

    @Mock
    private AuthorityService authorityService;

    private final File file = new FileModelRepository().getFile();
    private final User loggedUser = new UserModelRepository().getUser();

    @Test
    void givenExistentIdAndUserHasPermissionsWhenFindFileByIdThenReturnFile() {
        Long existentId = 1L;

        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.file);
        when(this.fileDao.findFileById(existentId)).thenReturn(this.file);

        assertThat(this.fileService.findFileById(existentId), is(this.file));
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenFindFileByIdThenThrowPermissionsException() {
        Long nonExistentId = 1L;

        when(this.fileDao.findFileById(nonExistentId)).thenReturn(this.file);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.file);

        assertThrows(PermissionsException.class, () -> this.fileService.findFileById(nonExistentId));
    }

    @Test
    void givenNonExistentIdWhenFindFileByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.fileDao.findFileById(nonExistentId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.fileService.findFileById(nonExistentId));
    }

    @Test
    void givenFileWhenCreateFileThenReturnFile() {
        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.fileDao.saveFile(this.file)).thenReturn(this.file);

        File file = this.fileService.createFile(this.file);
        assertThat(file, is(this.file));
        assertThat(file.getUser(), is(this.loggedUser));
    }

    @Test
    void givenExistentIdAndUserHasPermissionsWhenUpdateFileThenReturnUpdatedFile() {
        File fileToUpdate = mock(File.class);
        Long existentId = 1L;

        when(this.fileDao.findFileById(existentId)).thenReturn(fileToUpdate);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(fileToUpdate);
        when(this.fileDao.saveFile(fileToUpdate)).thenReturn(this.file);

        assertThat(this.fileService.updateFile(existentId, this.file), is(this.file));
        verify(fileToUpdate).setName(this.file.getName());
        verify(fileToUpdate).setType(this.file.getType());
        verify(fileToUpdate).setData(this.file.getData());
        verify(fileToUpdate).setUpdatedAt(any());
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenUpdateFileThenThrowPermissionsException() {
        Long nonExistentId = 1L;

        when(this.fileDao.findFileById(nonExistentId)).thenReturn(this.file);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.file);

        assertThrows(PermissionsException.class, () -> this.fileService.findFileById(nonExistentId));
    }

    @Test
    void givenNonExistentIdWhenUpdateFileThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.fileDao.findFileById(nonExistentId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.fileService.updateFile(nonExistentId, this.file));
    }

    @Test
    void givenExistentFileAndUserHasPermissionsWhenDeleteExerciseThenDoNothing() {
        Long existentId = 1L;

        when(this.fileDao.findFileById(existentId)).thenReturn(this.file);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.file);
        doNothing().when(this.fileDao).deleteFile(this.file);

        try {
            this.fileService.deleteFile(existentId);
        } catch (Exception e) {
            assertThat(e, is(nullValue()));
        }
    }

    @Test
    void givenExistentFileAndUserHasNotPermissionsWhenDeleteExerciseThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.fileDao.findFileById(existentId)).thenReturn(this.file);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.file);

        assertThrows(PermissionsException.class, () -> this.fileService.deleteFile(existentId));
    }

    @Test
    void givenNonExistentFileWhenDeleteExerciseThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.fileDao).findFileById(nonExistentId);

        assertThrows(NotFoundException.class, () -> this.fileService.deleteFile(nonExistentId));
    }

    @Test
    void givenSetPublicationWhenSetPublicationThenDoNothing() {
        File file = mock(File.class);
        Publication publication = mock(Publication.class);

        when(this.fileDao.saveFile(file)).thenReturn(file);

        try {
            this.fileService.setPublication(file, publication);
        } catch (Exception e) {
            assertThat(e, is(nullValue()));
        }
        verify(file).setPublication(publication);
    }
}
