package com.behabits.gymbo.domain.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private String entity;
    private Exercise exercise;
    private User user;
    private Training training;
}
