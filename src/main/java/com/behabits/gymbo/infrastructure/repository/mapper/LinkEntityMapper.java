package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.*;
import com.behabits.gymbo.infrastructure.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkEntityMapper {

    public List<Link> toDomain(List<LinkEntity> entities) {
        return entities != null ? entities.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Link toDomain(LinkEntity entity) {
        Link domain = new Link();
        domain.setId(entity.getId());
        domain.setEntity(entity.getEntity());
        if (entity.getExercise() != null) {
            Exercise exercise = new Exercise();
            exercise.setId(entity.getExercise().getId());
            domain.setExercise(exercise);
        }
        if (entity.getPlayer() != null) {
            User user = new User();
            user.setId(entity.getPlayer().getId());
            domain.setUser(user);
        }
        if (entity.getTraining() != null) {
            Training training = new Training();
            training.setId(entity.getTraining().getId());
            domain.setTraining(training);
        }
        Publication publication = new Publication();
        publication.setId(entity.getPublication().getId());
        domain.setPublication(publication);
        return domain;
    }

    public List<LinkEntity> toEntity(List<Link> models) {
        return models != null ? models.stream()
                .map(this::toEntity)
                .toList() : null;
    }

    public LinkEntity toEntity(Link domain) {
        LinkEntity entity = new LinkEntity();
        entity.setId(domain.getId());
        entity.setEntity(domain.getEntity());
        if (domain.getExercise() != null) {
            ExerciseEntity exerciseEntity = new ExerciseEntity();
            exerciseEntity.setId(domain.getExercise().getId());
            entity.setExercise(exerciseEntity);
        }
        if (domain.getUser() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(domain.getUser().getId());
            entity.setPlayer(userEntity);
        }
        if (domain.getTraining() != null) {
            TrainingEntity trainingEntity = new TrainingEntity();
            trainingEntity.setId(domain.getTraining().getId());
            entity.setTraining(trainingEntity);
        }
        if (domain.getPublication() != null) {
            PublicationEntity publicationEntity = new PublicationEntity();
            publicationEntity.setId(domain.getPublication().getId());
            entity.setPublication(publicationEntity);
        }
        return entity;
    }

}
