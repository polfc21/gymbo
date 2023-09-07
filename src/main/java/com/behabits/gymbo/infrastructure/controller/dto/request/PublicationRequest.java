package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.dto.validator.ValidSport;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationRequest {
    private String description;
    private List<Long> files;
    @Valid
    private List<LinkRequest> links;
    @ValidSport
    private String sport;
}
