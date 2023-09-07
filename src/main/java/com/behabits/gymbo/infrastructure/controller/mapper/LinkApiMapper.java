package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.infrastructure.controller.dto.request.LinkRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkApiMapper {

    private final ExerciseApiMapper exerciseApiMapper;
    private final UserApiMapper userApiMapper;

    public List<Link> toDomain(List<LinkRequest> requests) {
        return requests != null ? requests.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Link toDomain(LinkRequest request) {
        Link domain = new Link();
        domain.setEntity(request.getEntity());
        if (request.getExerciseId() != null) {
            Exercise exercise = new Exercise();
            exercise.setId(request.getExerciseId());
            domain.setExercise(exercise);
        }
        if (request.getUsername() != null) {
            User user = new User();
            user.setUsername(request.getUsername());
            domain.setUser(user);
        }
        return domain;
    }

    public List<LinkResponse> toResponse(List<Link> models) {
        return models != null ? models.stream()
                .map(this::toResponse)
                .toList() : null;
    }

    public LinkResponse toResponse(Link domain) {
        LinkResponse response = new LinkResponse();
        response.setId(domain.getId());
        response.setEntity(domain.getEntity());
        if (domain.getExercise() != null) {
            response.setExercise(this.exerciseApiMapper.toResponse(domain.getExercise()));
        }
        if (domain.getUser() != null) {
            response.setUser(this.userApiMapper.toResponse(domain.getUser()));
        }
        return response;
    }

}
