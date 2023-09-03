package com.behabits.gymbo.infrastructure.repository.entity;

import com.behabits.gymbo.domain.models.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercise")
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SerieEntity> series;
    @ManyToOne
    private TrainingEntity training;
    @ManyToOne
    private UserEntity player;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinkEntity> links;
    @Enumerated(EnumType.STRING)
    private Sport sport;
}
