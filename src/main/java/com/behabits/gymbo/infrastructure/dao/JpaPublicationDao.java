package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.infrastructure.repository.PublicationRepository;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.PublicationEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaPublicationDao implements PublicationDao {

    private final PublicationRepository publicationRepository;
    private final PublicationEntityMapper publicationEntityMapper;

    @Override
    public Publication savePublication(Publication publication) {
        PublicationEntity entityToCreate = this.publicationEntityMapper.toEntity(publication);
        PublicationEntity entityCreated = this.publicationRepository.save(entityToCreate);
        return this.publicationEntityMapper.toDomain(entityCreated);
    }

    @Override
    public Publication findPublicationById(Long id) {
        PublicationEntity entity = this.publicationRepository.findById(id)
                .orElse(null);
        return entity != null ? this.publicationEntityMapper.toDomain(entity) : null;
    }

}
