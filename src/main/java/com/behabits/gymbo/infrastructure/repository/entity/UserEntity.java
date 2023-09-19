package com.behabits.gymbo.infrastructure.repository.entity;

import com.behabits.gymbo.domain.models.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "player")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingEntity> trainings;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseEntity> exercises;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TokenEntity> tokens;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> files;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationEntity> locations;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublicationEntity> publications;
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;
    @OneToMany(mappedBy = "reviewed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviewers;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinkEntity> links;
    @ElementCollection(targetClass = Sport.class)
    @CollectionTable(name = "player_sports", joinColumns = @JoinColumn(name = "player_id"))
    @Enumerated(EnumType.STRING)
    private Set<Sport> sports;
}
