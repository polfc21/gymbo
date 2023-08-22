package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewRequest {
    private String comment;
    @Min(1)
    @Max(5)
    private Integer rating;
    private String usernameReviewed;
}
