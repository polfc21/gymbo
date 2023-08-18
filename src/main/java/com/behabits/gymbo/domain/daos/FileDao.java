package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.File;

public interface FileDao {
    File saveFile(File file);
    File findFileById(Long id);
    void deleteFile(File file);
}
