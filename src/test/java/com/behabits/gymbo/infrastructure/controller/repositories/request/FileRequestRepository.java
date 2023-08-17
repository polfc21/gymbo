package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.FileRequest;
import lombok.NoArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
public class FileRequestRepository {

    public FileRequest getCorrectFileRequest() {
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "file".getBytes());
        return new FileRequest(file);
    }

    public FileRequest getIncorrectFileRequest() {
        return new FileRequest(null);
    }

}
