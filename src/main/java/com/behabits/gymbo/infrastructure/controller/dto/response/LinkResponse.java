package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponse {
    private Long id;
    private String entity;
    private ExerciseResponse exercise;
    private UserResponse user;
}
