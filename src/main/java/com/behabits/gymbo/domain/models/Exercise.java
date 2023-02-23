package com.behabits.gymbo.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    private Long id;
    private String name;
    private List<Serie> series;
}
