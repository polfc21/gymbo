package com.behabits.gymbo.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "serie")
public class SerieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer number;
    private Integer repetitions;
    private Double weight;
    @ManyToOne
    private ExerciseEntity exercise;
}
