package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.LinkDao;
import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.infrastructure.repository.LinkRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.LinkEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaLinkDao implements LinkDao {

    private final LinkRepository linkRepository;
    private final LinkEntityMapper mapper;

    @Override
    public void deleteLink(Link link) {
        this.linkRepository.deleteById(link.getId());
    }

    @Override
    public Link findLinkById(Long id) {
        LinkEntity linkEntity = this.linkRepository.findById(id)
                .orElse(null);
        return linkEntity != null ? this.mapper.toDomain(linkEntity) : null;
    }

}
