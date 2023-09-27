package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Publication;

import java.util.List;

public interface PublicationDao {
    Publication savePublication(Publication publication);
    Publication findPublicationById(Long id);
    List<Publication> findAllPublications();
    void deletePublication(Publication publication);
}
