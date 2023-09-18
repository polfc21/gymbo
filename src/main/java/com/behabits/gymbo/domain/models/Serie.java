package com.behabits.gymbo.domain.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Serie {
    private Long id;
    private Integer number;
    private Double weight;
    private Integer repetitions;
    private Exercise exercise;
}
