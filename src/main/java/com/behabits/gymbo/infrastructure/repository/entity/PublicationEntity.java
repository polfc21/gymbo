package com.behabits.gymbo.infrastructure.repository.entity;

import com.behabits.gymbo.domain.models.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "publication")
public class PublicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private UserEntity player;
    @OneToMany(mappedBy = "publication")
    private List<FileEntity> files;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinkEntity> links;
    @Enumerated(EnumType.STRING)
    private Sport sport;
}
