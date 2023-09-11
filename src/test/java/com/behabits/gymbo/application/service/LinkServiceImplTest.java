package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.LinkDao;
import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.domain.services.TrainingService;
import com.behabits.gymbo.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @InjectMocks
    private LinkServiceImpl linkService;

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private UserService userService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private LinkDao linkDao;

    @Mock
    private AuthorityService authorityService;

    @Test
    void givenLinksWithExistentExerciseWhenSetLinksThenSetLinks() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Exercise exercise = mock(Exercise.class);
        Long exerciseId = 1L;

        when(link.getEntity()).thenReturn("EXERCISE");
        when(link.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(exerciseId);
        when(this.exerciseService.findExerciseById(exerciseId)).thenReturn(exercise);
        this.linkService.setLinks(links);

        verify(link).setExercise(exercise);
    }

    @Test
    void givenLinksWithNonExistentExerciseWhenSetLinksThenThrowNotFoundException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Exercise exercise = mock(Exercise.class);
        Long exerciseId = 1L;

        when(link.getEntity()).thenReturn("EXERCISE");
        when(link.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(exerciseId);
        when(this.exerciseService.findExerciseById(exerciseId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenNotOwnExerciseLinksWhenSetLinksThenThrowPermissionsException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Exercise exercise = mock(Exercise.class);
        Long exerciseId = 1L;

        when(link.getEntity()).thenReturn("EXERCISE");
        when(link.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(exerciseId);
        when(this.exerciseService.findExerciseById(exerciseId)).thenThrow(PermissionsException.class);

        assertThrows(PermissionsException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenIncorrectExerciseLinksWhenSetLinksThenThrowRuntimeException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(link.getEntity()).thenReturn("EXERCISE");
        when(link.getExercise()).thenReturn(null);

        assertThrows(IncorrectLinkException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenLinksWithExistentUserWhenSetLinksThenSetLinks() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        User user = mock(User.class);
        String username = "username";

        when(link.getEntity()).thenReturn("USER");
        when(link.getUser()).thenReturn(user);
        when(link.getUser().getUsername()).thenReturn(username);
        when(this.userService.findUserByUsername(username)).thenReturn(user);
        this.linkService.setLinks(links);

        verify(link).setUser(user);
    }

    @Test
    void givenLinksWithNonExistentUserWhenSetLinksThenThrowNotFoundException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        User user = mock(User.class);
        String username = "username";

        when(link.getEntity()).thenReturn("USER");
        when(link.getUser()).thenReturn(user);
        when(link.getUser().getUsername()).thenReturn(username);
        when(this.userService.findUserByUsername(username)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenIncorrectUserLinksWhenSetLinksThenThrowRuntimeException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(link.getEntity()).thenReturn("USER");
        when(link.getUser()).thenReturn(null);

        assertThrows(IncorrectLinkException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenLinksWithExistentTrainingWhenSetLinksThenSetLinks() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Training training = mock(Training.class);
        Long trainingId = 1L;

        when(link.getEntity()).thenReturn("TRAINING");
        when(link.getTraining()).thenReturn(training);
        when(training.getId()).thenReturn(trainingId);
        when(this.trainingService.findTrainingById(trainingId)).thenReturn(training);
        this.linkService.setLinks(links);

        verify(link).setTraining(training);
    }

    @Test
    void givenLinksWithNonExistentTrainingWhenSetLinksThenThrowNotFoundException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Training training = mock(Training.class);
        Long trainingId = 1L;

        when(link.getEntity()).thenReturn("TRAINING");
        when(link.getTraining()).thenReturn(training);
        when(training.getId()).thenReturn(trainingId);
        when(this.trainingService.findTrainingById(trainingId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenNotOwnTrainingLinksWhenSetLinksThenThrowPermissionsException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Training training = mock(Training.class);
        Long trainingId = 1L;

        when(link.getEntity()).thenReturn("TRAINING");
        when(link.getTraining()).thenReturn(training);
        when(training.getId()).thenReturn(trainingId);
        when(this.trainingService.findTrainingById(trainingId)).thenThrow(PermissionsException.class);

        assertThrows(PermissionsException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenIncorrectTrainingLinksWhenSetLinksThenThrowRuntimeException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);

        when(link.getEntity()).thenReturn("TRAINING");
        when(link.getTraining()).thenReturn(null);

        assertThrows(IncorrectLinkException.class, () -> this.linkService.setLinks(links));
    }

    @Test
    void givenExistentLinkWithPermissionsWhenDeleteLinkThenDeleteLink() {
        Link link = mock(Link.class);
        Long linkId = 1L;

        when(this.linkDao.findLinkById(linkId)).thenReturn(link);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(link);
        doNothing().when(this.linkDao).deleteLink(link);

        try {
            this.linkService.deleteLink(linkId);
        } catch (Exception e) {
            fail("Should not throw any exception");
        }
    }

    @Test
    void givenExistentLinkWithoutPermissionsWhenDeleteLinkThenThrowPermissionsException() {
        Link link = mock(Link.class);
        Long linkId = 1L;

        when(this.linkDao.findLinkById(linkId)).thenReturn(link);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(link);

        assertThrows(PermissionsException.class, () -> this.linkService.deleteLink(linkId));
    }

    @Test
    void givenNonExistentLinkWhenDeleteLinkThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.linkDao).findLinkById(nonExistentId);

        assertThrows(NotFoundException.class, () -> this.linkService.deleteLink(nonExistentId));
    }

}
