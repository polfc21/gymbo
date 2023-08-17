package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private Long id;
    private String name;
    private String type;
    private byte[] data;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
