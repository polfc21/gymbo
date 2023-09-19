package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.LinkDao;
import com.behabits.gymbo.domain.exceptions.IncorrectLinkException;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final ExerciseService exerciseService;
    private final UserService userService;
    private final TrainingService trainingService;
    private final LinkDao linkDao;
    private final AuthorityService authorityService;

    @Override
    public void setLinks(List<Link> links) {
        links.forEach(link -> {
            switch (link.getEntity()) {
                case "EXERCISE" -> this.setExerciseToLink(link);
                case "USER" -> this.setUserToLink(link);
                case "TRAINING" -> this.setTrainingToLink(link);
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

    private void setTrainingToLink(Link link) {
        if (link.getTraining() == null) {
            throw new IncorrectLinkException("Incorrect training link");
        }
        Training training = this.trainingService.findTrainingById(link.getTraining().getId());
        link.setTraining(training);
    }

    @Override
    public void deleteLink(Long id) {
        Link link = this.linkDao.findLinkById(id);
        if (link == null) {
            throw new NotFoundException("Link not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(link);
        this.linkDao.deleteLink(link);
    }

}
