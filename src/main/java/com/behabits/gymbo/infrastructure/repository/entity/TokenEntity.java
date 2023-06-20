package com.behabits.gymbo.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 1000)
    private String token;
    private Boolean revoked;
    private Boolean expired;
    @ManyToOne
    private UserEntity player;
}
