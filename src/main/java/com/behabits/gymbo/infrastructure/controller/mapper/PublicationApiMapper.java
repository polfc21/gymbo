package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicationApiMapper {

    private final UserApiMapper userApiMapper;
    private final FileApiMapper fileApiMapper;
    private final LinkApiMapper linkApiMapper;
    private final SportApiMapper sportApiMapper;

    public Publication toDomain(PublicationRequest request) {
        Publication domain = new Publication();
        domain.setDescription(request.getDescription());
        if (request.getLinks() != null) {
            domain.setLinks(this.linkApiMapper.toDomain(request.getLinks()));
        }
        domain.setSport(this.sportApiMapper.toDomain(request.getSport()));
        return domain;
    }

    public PublicationResponse toResponse(Publication domain) {
        PublicationResponse response = new PublicationResponse();
        response.setId(domain.getId());
        response.setDescription(domain.getDescription());
        response.setCreatedAt(domain.getCreatedAt());
        response.setUpdatedAt(domain.getUpdatedAt());
        response.setPostedBy(this.userApiMapper.toResponse(domain.getPostedBy()));
        if (domain.getFiles() != null) {
            response.setFiles(this.fileApiMapper.toResponse(domain.getFiles()));
        }
        if (domain.getLinks() != null) {
            response.setLinks(this.linkApiMapper.toResponse(domain.getLinks()));
        }
        response.setSport(domain.getSport());
        return response;
    }
}
