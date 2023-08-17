package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.infrastructure.controller.dto.request.FileRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.FileResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileApiMapper {

    public File toDomain(FileRequest request) throws IOException {
        File domain = new File();
        MultipartFile file = request.getFile();
        domain.setName(file.getOriginalFilename());
        domain.setType(file.getContentType());
        domain.setData(file.getBytes());
        return domain;
    }

    public FileResponse toResponse(File domain) {
        FileResponse response = new FileResponse();
        response.setId(domain.getId());
        response.setName(domain.getName());
        response.setType(domain.getType());
        response.setData(domain.getData());
        response.setCreatedAt(domain.getCreatedAt());
        response.setUpdatedAt(domain.getUpdatedAt());
        return response;
    }

}
