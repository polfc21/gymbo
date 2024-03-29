package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.FileService;
import com.behabits.gymbo.domain.services.LinkService;
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
    private final LinkService linkService;

    @Override
    public Publication findPublicationById(Long id) {
        Publication publication = this.publicationDao.findPublicationById(id);
        if (publication == null) {
            throw new NotFoundException("Publication not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(publication);
        return publication;
    }

    @Transactional
    @Override
    public Publication createPublication(Publication publication, List<Long> files) {
        User user = this.authorityService.getLoggedUser();
        publication.setPostedBy(user);
        publication.setCreatedAt(LocalDateTime.now());
        List<File> filesToSet = this.getFiles(files);
        this.linkService.setLinks(publication.getLinks());
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

    @Override
    public Publication updatePublication(Long id, Publication publication) {
        Publication publicationToUpdate = this.findPublicationById(id);
        publicationToUpdate.setDescription(publication.getDescription());
        publicationToUpdate.setSport(publication.getSport());
        publicationToUpdate.setUpdatedAt(LocalDateTime.now());
        return this.publicationDao.savePublication(publicationToUpdate);
    }

    @Override
    public void deleteLink(Long id) {
        this.linkService.deleteLink(id);
    }

    @Override
    public Publication addLink(Long id, Link link) {
        Publication publicationToUpdate = this.findPublicationById(id);
        this.linkService.setLinks(List.of(link));
        publicationToUpdate.addLink(link);
        return this.publicationDao.savePublication(publicationToUpdate);
    }

    @Override
    public List<Publication> findAllPublications() {
        return this.publicationDao.findAllPublications();
    }

    @Override
    public void deletePublication(Long id) {
        Publication publicationToDelete = this.findPublicationById(id);
        this.publicationDao.deletePublication(publicationToDelete);
    }

}
