package com.behabits.gymbo.domain.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    private Long id;
    private String name;
    private List<Serie> series;
    private User user;
}
