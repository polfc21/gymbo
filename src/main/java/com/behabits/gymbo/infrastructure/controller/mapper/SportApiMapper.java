package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Sport;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SportApiMapper {

    public Set<Sport> toDomain(Set<String> requests) {
        return requests.stream()
                .map(this::toDomain)
                .collect(Collectors.toSet());
    }

    public Sport toDomain(String request) {
        return request == null ? null : Sport.valueOf(request.toUpperCase());
    }

}
