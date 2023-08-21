package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.repositories.PublicationModelRepository;
import com.behabits.gymbo.infrastructure.repository.PublicationRepository;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.PublicationEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.PublicationEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaPublicationDaoTest {

    @InjectMocks
    private JpaPublicationDao publicationDao;

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private PublicationEntityMapper publicationEntityMapper;

    private final Publication publication = new PublicationModelRepository().getPublication();
    private final PublicationEntity publicationEntity = new PublicationEntityRepository().getPublication();

    @Test
    void givenPublicationWhenSavePublicationThenReturnPublication() {
        when(this.publicationEntityMapper.toEntity(this.publication)).thenReturn(this.publicationEntity);
        when(this.publicationRepository.save(this.publicationEntity)).thenReturn(this.publicationEntity);
        when(this.publicationEntityMapper.toDomain(this.publicationEntity)).thenReturn(this.publication);

        assertThat(this.publicationDao.savePublication(this.publication), is(this.publication));
    }
}
