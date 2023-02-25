package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SerieApiMapper {

    public List<Serie> toDomain(List<SerieRequest> requests) {
        return requests.stream()
                .map(this::toDomain)
                .toList();
    }

    private Serie toDomain(SerieRequest request) {
        Serie domain = new Serie();
        domain.setId(request.getId());
        domain.setNumber(request.getNumber());
        domain.setRepetitions(request.getRepetitions());
        domain.setWeight(request.getWeight());
        return domain;
    }

    public List<SerieResponse> toResponse(List<Serie> models) {
        return models.stream()
                .map(this::toResponse)
                .toList();
    }

    private SerieResponse toResponse(Serie domain) {
        SerieResponse response = new SerieResponse();
        response.setId(domain.getId());
        response.setNumber(domain.getNumber());
        response.setRepetitions(domain.getRepetitions());
        response.setWeight(domain.getWeight());
        return response;
    }
}
