package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.repositories.LinkModelRepository;
import com.behabits.gymbo.infrastructure.repository.LinkRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.LinkEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.LinkEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class JpaLinkDaoTest {

    @InjectMocks
    private JpaLinkDao linkDao;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private LinkEntityMapper mapper;

    private final Link link = new LinkModelRepository().getLinkWithExercise();
    private final LinkEntity linkEntity = new LinkEntityRepository().getLinkWithExercise();

    @Test
    void givenLinkWhenDeleteLinkThenDeleteLink() {
        this.linkDao.deleteLink(this.link);

        verify(this.linkRepository, times(1)).deleteById(this.link.getId());
    }

    @Test
    void givenExistentLinkWhenFindLinkByIdThenReturnLink() {
        Long existentId = 1L;

        when(this.linkRepository.findById(existentId)).thenReturn(Optional.of(this.linkEntity));
        when(this.mapper.toDomain(this.linkEntity)).thenReturn(this.link);

        assertThat(this.linkDao.findLinkById(existentId), is(this.link));
    }

    @Test
    void givenNonExistentLinkWhenFindLinkByIdThenReturnNull() {
        Long nonExistentId = 1L;

        when(this.linkRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertNull(this.linkDao.findLinkById(nonExistentId));
    }

}
