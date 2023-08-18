package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.File;

public interface FileService {
    File createFile(File file);
    File findFileById(Long id);
    File updateFile(Long id, File file);
    void deleteFile(Long id);
}
