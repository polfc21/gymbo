package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.dto.validator.ValidFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FileRequest {
    @ValidFile
    private MultipartFile file;
}
