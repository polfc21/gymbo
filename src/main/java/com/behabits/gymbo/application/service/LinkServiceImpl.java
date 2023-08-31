package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.domain.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final ExerciseService exerciseService;

    @Override
    public void setExercises(List<Link> links) {
        links.forEach(link -> {
            Exercise exercise = this.exerciseService.findExerciseById(link.getExercise().getId());
            link.setExercise(exercise);
        });
    }

}
