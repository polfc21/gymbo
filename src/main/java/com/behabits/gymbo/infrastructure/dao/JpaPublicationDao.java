package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.PublicationDao;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.infrastructure.repository.PublicationRepository;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.PublicationEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaPublicationDao implements PublicationDao {

    private final PublicationRepository publicationRepository;
    private final PublicationEntityMapper publicationEntityMapper;

    @Override
    public Publication savePublication(Publication publication) {
        PublicationEntity entityToSave = this.publicationEntityMapper.toEntity(publication);
        PublicationEntity entitySaved = this.publicationRepository.save(entityToSave);
        return this.publicationEntityMapper.toDomain(entitySaved);
    }

    @Override
    public Publication findPublicationById(Long id) {
        PublicationEntity entity = this.publicationRepository.findById(id)
                .orElse(null);
        return entity != null ? this.publicationEntityMapper.toDomain(entity) : null;
    }

    @Override
    public List<Publication> findAllPublications() {
        return this.publicationRepository.findAll()
                .stream()
                .map(this.publicationEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void deletePublication(Publication publication) {
        this.publicationRepository.deleteById(publication.getId());
    }

}
