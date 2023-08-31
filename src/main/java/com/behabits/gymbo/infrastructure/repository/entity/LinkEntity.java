package com.behabits.gymbo.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String entity;
    @ManyToOne
    private PublicationEntity publication;
    @ManyToOne
    private ExerciseEntity exercise;
}
