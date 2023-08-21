package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Publication;

public interface FileService {
    File createFile(File file);
    File findFileById(Long id);
    File updateFile(Long id, File file);
    void deleteFile(Long id);
    void setPublication(File file, Publication publication);
}
