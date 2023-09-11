package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.LinkDao;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.infrastructure.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaLinkDao implements LinkDao {

    private final LinkRepository linkRepository;

    @Override
    public void deleteLink(Link link) {
        this.linkRepository.deleteById(link.getId());
    }

}
