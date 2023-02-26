package com.behabits.gymbo.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Serie {
    private Long id;
    private Integer number;
    private Double weight;
    private Integer repetitions;
}
