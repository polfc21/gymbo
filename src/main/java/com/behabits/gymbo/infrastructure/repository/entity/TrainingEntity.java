package com.behabits.gymbo.infrastructure.repository.entity;

import com.behabits.gymbo.domain.models.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "training")
public class TrainingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Column(name = "training_date")
    private LocalDateTime trainingDate;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseEntity> exercises;
    @ManyToOne
    private UserEntity player;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinkEntity> links;
    @Enumerated(EnumType.STRING)
    private Sport sport;
}
