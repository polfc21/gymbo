package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.FileDao;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;
    private final AuthorityService authorityService;


    @Override
    public File createFile(File file) {
        User user = this.authorityService.getLoggedUser();
        file.setUser(user);
        file.setCreatedAt(LocalDateTime.now());
        file.setUpdatedAt(LocalDateTime.now());
        return this.fileDao.saveFile(file);
    }

    @Transactional
    @Override
    public File findFileById(Long id) {
        File file = this.fileDao.findFileById(id);
        this.authorityService.checkLoggedUserHasPermissions(file);
        return file;
    }

    @Override
    public File updateFile(Long id, File file) {
        File fileToUpdate = this.findFileById(id);
        fileToUpdate.setName(file.getName());
        fileToUpdate.setType(file.getType());
        fileToUpdate.setData(file.getData());
        fileToUpdate.setUpdatedAt(LocalDateTime.now());
        return this.fileDao.saveFile(fileToUpdate);
    }

    @Override
    public void deleteFile(Long id) {
        File file = this.findFileById(id);
        this.fileDao.deleteFile(file);
    }

}
