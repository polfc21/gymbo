package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Link;

import java.util.List;

public interface LinkService {
    void setLinks(List<Link> links);
    void deleteLink(Long id);
}
