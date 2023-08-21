package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.FileService;
import com.behabits.gymbo.domain.services.PublicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationDao publicationDao;
    private final AuthorityService authorityService;
    private final FileService fileService;

    @Transactional
    @Override
    public Publication createPublication(Publication publication, List<Long> files) {
        User user = this.authorityService.getLoggedUser();
        publication.setPostedBy(user);
        publication.setCreatedAt(LocalDateTime.now());
        List<File> filesToSet = this.getFiles(files);
        Publication publicationCreated = this.publicationDao.savePublication(publication);
        filesToSet.forEach(file -> this.fileService.setPublication(file, publicationCreated));
        publicationCreated.setFiles(filesToSet);
        return publicationCreated;
    }

    private List<File> getFiles(List<Long> filesIds) {
        return filesIds.stream()
                .map(this.fileService::findFileById)
                .toList();
    }

}
