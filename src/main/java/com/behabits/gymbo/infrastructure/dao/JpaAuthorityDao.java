package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.*;
import com.behabits.gymbo.infrastructure.repository.*;
import com.behabits.gymbo.infrastructure.repository.entity.*;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JpaAuthorityDao implements AuthorityDao {

    private static final String USER_HAS_NOT_PERMISSIONS = "User doesn't have permissions";

    private final UserEntityMapper userMapper;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;
    private final SerieRepository serieRepository;
    private final FileRepository fileRepository;
    private final LocationRepository locationRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity loggedUser = this.userRepository.findByUsername(authentication.getName());
        return this.userMapper.toDomain(loggedUser);
    }

    @Override
    public void checkLoggedUserHasPermissions(Training training) {
        Long loggedUserId = this.getLoggedUser().getId();
        TrainingEntity trainingToCheck = this.trainingRepository.findByIdAndPlayerId(training.getId(), loggedUserId);
        if (trainingToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

    @Override
    public void checkLoggedUserHasPermissions(Exercise exercise) {
        Long loggedUserId = this.getLoggedUser().getId();
        ExerciseEntity exerciseToCheck = this.exerciseRepository.findByIdAndPlayerId(exercise.getId(), loggedUserId);
        if (exerciseToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

    @Override
    public void checkLoggedUserHasPermissions(Serie serie) {
        Long loggedUserId = this.getLoggedUser().getId();
        SerieEntity serieToCheck = this.serieRepository.findByIdAndPlayerId(serie.getId(), loggedUserId);
        if (serieToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

    @Override
    public void checkLoggedUserHasPermissions(File file) {
        Long loggedUserId = this.getLoggedUser().getId();
        FileEntity fileToCheck = this.fileRepository.findByIdAndPlayerId(file.getId(), loggedUserId);
        if (fileToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

    @Override
    public void checkLoggedUserHasPermissions(Location location) {
        Long loggedUserId = this.getLoggedUser().getId();
        LocationEntity locationToCheck = this.locationRepository.findByIdAndPlayerId(location.getId(), loggedUserId);
        if (locationToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

    @Override
    public void checkLoggedUserHasPermissions(Review review) {
        Long loggedUserId = this.getLoggedUser().getId();
        ReviewEntity reviewToCheck = this.reviewRepository.findByIdAndPlayerIsReviewerOrReviewed(review.getId(), loggedUserId);
        if (reviewToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

}
