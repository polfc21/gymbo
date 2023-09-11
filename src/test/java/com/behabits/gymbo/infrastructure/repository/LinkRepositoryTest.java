package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.LinkEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.PublicationEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class LinkRepositoryTest {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();
    private final PublicationEntity publication = new PublicationEntityRepository().getPublication();
    private final LinkEntity link = new LinkEntityRepository().getLinkWithExercise();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.publication.setPlayer(this.player);
        this.publication.setId(null);
        this.entityManager.persist(this.publication);
        this.link.setPublication(this.publication);
        this.link.setExercise(null);
        this.link.setId(null);
        this.entityManager.persist(this.link);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.link);
        this.entityManager.remove(this.publication);
        this.entityManager.remove(this.player);
    }

    @Test
    void givenIdAndPlayerIdAreAllPresentWhenFindByIdAndPlayerIdThenReturnLink() {
        LinkEntity linkFound = this.linkRepository.findByIdAndPlayerId(this.link.getId(), this.player.getId());

        assertThat(linkFound, is(this.link));
    }

    @Test
    void givenIdAndPlayerIdAndPlayerIdIsNotPresentWhenFindByIdAndPlayerIdThenReturnNull() {
        LinkEntity linkFound = this.linkRepository.findByIdAndPlayerId(this.link.getId(), 0L);

        assertNull(linkFound);
    }

    @Test
    void givenIdAndPlayerIdAndIdIsNotPresentWhenFindByIdAndPlayerIdThenReturnNull() {
        LinkEntity linkFound = this.linkRepository.findByIdAndPlayerId(0L, this.player.getId());

        assertNull(linkFound);
    }

    @Test
    void givenIdAndPlayerIdAndIdAndPlayerIdAreNotPresentWhenFindByIdAndPlayerIdThenReturnNull() {
        LinkEntity linkFound = this.linkRepository.findByIdAndPlayerId(0L, 0L);

        assertNull(linkFound);
    }
}
