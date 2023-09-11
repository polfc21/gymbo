package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.repositories.LinkModelRepository;
import com.behabits.gymbo.infrastructure.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JpaLinkDaoTest {

    @InjectMocks
    private JpaLinkDao linkDao;

    @Mock
    private LinkRepository linkRepository;

    private final Link link = new LinkModelRepository().getLinkWithExercise();

    @Test
    void givenLinkWhenDeleteLinkThenDeleteLink() {
        this.linkDao.deleteLink(this.link);

        verify(this.linkRepository, times(1)).deleteById(this.link.getId());
    }

}
