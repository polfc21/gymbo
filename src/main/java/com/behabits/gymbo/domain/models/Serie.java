package com.behabits.gymbo.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Serie {
    private Long id;
    private Integer number;
    private Double weight;
    private Integer repetitions;
}
