package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseApiMapper {

    private final SerieApiMapper serieApiMapper;

    public List<Exercise> toDomain(List<ExerciseRequest> requests) {
        return requests != null ? requests.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Exercise toDomain(ExerciseRequest request) {
        Exercise domain = new Exercise();
        domain.setId(request.getId());
        domain.setName(request.getName());
        domain.setSeries(this.serieApiMapper.toDomain(request.getSeries()));
        return domain;
    }

    public List<ExerciseResponse> toResponse(List<Exercise> models) {
        return models != null ? models.stream()
                .map(this::toResponse)
                .toList() : null;
    }

    public ExerciseResponse toResponse(Exercise domain) {
        ExerciseResponse response = new ExerciseResponse();
        response.setId(domain.getId());
        response.setName(domain.getName());
        response.setSeries(this.serieApiMapper.toResponse(domain.getSeries()));
        return response;
    }
}
