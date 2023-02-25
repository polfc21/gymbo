package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingApiMapper {

    private final ExerciseApiMapper exerciseApiMapper;

    public Training toDomain(TrainingRequest request) {
        Training domain = new Training();
        domain.setId(request.getId());
        domain.setName(request.getName());
        domain.setTrainingDate(request.getTrainingDate());
        domain.setExercises(this.exerciseApiMapper.toDomain(request.getExercises()));
        return domain;
    }

    public TrainingResponse toResponse(Training domain) {
        TrainingResponse response = new TrainingResponse();
        response.setId(domain.getId());
        response.setName(domain.getName());
        response.setExercises(this.exerciseApiMapper.toResponse(domain.getExercises()));
        return response;
    }
}
