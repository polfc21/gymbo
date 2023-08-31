package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.services.ExerciseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @InjectMocks
    private LinkServiceImpl linkService;

    @Mock
    private ExerciseService exerciseService;

    @Test
    void givenLinksWithExistentExerciseWhenSetExercisesThenSetExercises() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Exercise exercise = mock(Exercise.class);
        Long exerciseId = 1L;

        when(link.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(exerciseId);
        when(this.exerciseService.findExerciseById(exerciseId)).thenReturn(exercise);
        this.linkService.setExercises(links);

        verify(link).setExercise(exercise);
    }

    @Test
    void givenLinksWithNonExistentExerciseWhenSetExercisesThenThrowNotFoundException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Exercise exercise = mock(Exercise.class);
        Long exerciseId = 1L;

        when(link.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(exerciseId);
        when(this.exerciseService.findExerciseById(exerciseId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.linkService.setExercises(links));
    }

    @Test
    void givenNotOwnExerciseLinksWhenSetExercisesThenThrowPermissionsException() {
        Link link = mock(Link.class);
        List<Link> links = List.of(link);
        Exercise exercise = mock(Exercise.class);
        Long exerciseId = 1L;

        when(link.getExercise()).thenReturn(exercise);
        when(exercise.getId()).thenReturn(exerciseId);
        when(this.exerciseService.findExerciseById(exerciseId)).thenThrow(PermissionsException.class);

        assertThrows(PermissionsException.class, () -> this.linkService.setExercises(links));
    }
}
