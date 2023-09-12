package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;

import java.util.List;

public interface PublicationService {
    Publication createPublication(Publication publication, List<Long> files);
    Publication updatePublication(Long id, Publication publication);
    void deleteLink(Long id);
    Publication addLink(Long id, Link link);
}
