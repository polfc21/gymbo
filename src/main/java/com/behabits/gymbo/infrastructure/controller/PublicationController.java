package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.services.PublicationService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.PublicationApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
@RestController
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationApiMapper mapper;

    @PostMapping
    public ResponseEntity<PublicationResponse> createPublication(@RequestBody PublicationRequest request) {
        Publication publicationToCreate = this.mapper.toDomain(request);
        Publication publicationCreated = this.publicationService.createPublication(publicationToCreate, request.getFiles());
        return new ResponseEntity<>(this.mapper.toResponse(publicationCreated), HttpStatus.CREATED);
    }

}
