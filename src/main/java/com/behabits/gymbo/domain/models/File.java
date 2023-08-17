package com.behabits.gymbo.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private Long id;
    private String name;
    private String type;
    private byte[] data;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
}
