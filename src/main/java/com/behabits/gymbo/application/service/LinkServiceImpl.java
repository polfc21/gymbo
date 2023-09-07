package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.domain.services.LinkService;
import com.behabits.gymbo.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final ExerciseService exerciseService;
    private final UserService userService;

    @Override
    public void setLinks(List<Link> links) {
        links.forEach(link -> {
            switch (link.getEntity()) {
                case "EXERCISE" -> this.setExerciseToLink(link);
                case "USER" -> this.setUserToLink(link);
            }
        });
    }

    private void setExerciseToLink(Link link) {
        if (link.getExercise() == null) {
            throw new IncorrectLinkException("Incorrect exercise link");
        }
        Exercise exercise = this.exerciseService.findExerciseById(link.getExercise().getId());
        link.setExercise(exercise);
    }

    private void setUserToLink(Link link) {
        if (link.getUser() == null) {
            throw new IncorrectLinkException("Incorrect user link");
        }
        User user = this.userService.findUserByUsername(link.getUser().getUsername());
        link.setUser(user);
    }

}
