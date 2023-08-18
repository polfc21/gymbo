package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.IncorrectFileException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.services.FileService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.FileRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.FileResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.FileApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.FILES)
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    private final FileApiMapper mapper;

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<byte[]> findFileById(@PathVariable Long id) {
        File file = this.fileService.findFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @PostMapping
    public ResponseEntity<FileResponse> createFile(@ModelAttribute @Valid FileRequest request) {
        File file = this.fileService.createFile(this.mapToDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(file), HttpStatus.CREATED);
    }

    private File mapToDomain(FileRequest request) {
        try {
            return this.mapper.toDomain(request);
        } catch (IOException e) {
            throw new IncorrectFileException("Incorrect file");
        }
    }

    @PutMapping(ApiConstant.ID)
    public ResponseEntity<FileResponse> updateFile(@PathVariable Long id, @ModelAttribute @Valid FileRequest request) {
        File file = this.fileService.updateFile(id, this.mapToDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(file), HttpStatus.OK);
    }

    @DeleteMapping(ApiConstant.ID)
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        this.fileService.deleteFile(id);
        return new ResponseEntity<>("File with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }

}
