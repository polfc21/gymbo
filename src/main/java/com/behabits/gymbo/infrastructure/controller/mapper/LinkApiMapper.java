package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.infrastructure.controller.dto.request.LinkRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LinkResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinkApiMapper {

    public List<Link> toDomain(List<LinkRequest> requests) {
        return requests != null ? requests.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Link toDomain(LinkRequest request) {
        Link domain = new Link();
        domain.setEntity(request.getEntity());
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
        return response;
    }

}
