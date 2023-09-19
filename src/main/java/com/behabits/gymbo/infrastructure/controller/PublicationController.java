package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.services.PublicationService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.LinkRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.LinkApiMapper;
import com.behabits.gymbo.infrastructure.controller.mapper.PublicationApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
@RestController
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationApiMapper mapper;
    private final LinkApiMapper linkMapper;

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<PublicationResponse> findPublicationById(@PathVariable Long id) {
        Publication publication = this.publicationService.findPublicationById(id);
        return new ResponseEntity<>(this.mapper.toResponse(publication), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PublicationResponse> createPublication(@RequestBody @Valid PublicationRequest request) {
        Publication publicationToCreate = this.mapper.toDomain(request);
        Publication publicationCreated = this.publicationService.createPublication(publicationToCreate, request.getFiles());
        return new ResponseEntity<>(this.mapper.toResponse(publicationCreated), HttpStatus.CREATED);
    }

    @PutMapping(ApiConstant.ID)
    public ResponseEntity<PublicationResponse> updatePublication(@RequestBody PublicationRequest request, @PathVariable Long id) {
        Publication publicationToUpdate = this.mapper.toDomain(request);
        Publication publicationUpdated = this.publicationService.updatePublication(id, publicationToUpdate);
        return new ResponseEntity<>(this.mapper.toResponse(publicationUpdated), HttpStatus.OK);
    }

    @DeleteMapping(ApiConstant.LINKS + ApiConstant.ID)
    public ResponseEntity<String> deleteLink(@PathVariable Long id) {
        this.publicationService.deleteLink(id);
        return new ResponseEntity<>("Link with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping(ApiConstant.ID + ApiConstant.LINKS)
    public ResponseEntity<PublicationResponse> addLink(@PathVariable Long id, @RequestBody @Valid LinkRequest linkRequest) {
        Link link = this.linkMapper.toDomain(linkRequest);
        Publication publication = this.publicationService.addLink(id, link);
        return new ResponseEntity<>(this.mapper.toResponse(publication), HttpStatus.CREATED);
    }

}
