package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
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
class PublicationRepositoryTest {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity user = new UserEntityRepository().getUser();
    private final PublicationEntity publication = new PublicationEntityRepository().getPublication();

    @BeforeEach
    void setUp() {
        this.user.setId(null);
        this.entityManager.persist(this.user);
        this.publication.setPlayer(this.user);
        this.publication.setId(null);
        this.entityManager.persist(this.publication);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.publication);
        this.entityManager.remove(this.user);
    }

    @Test
    void givenPublicationOfUserWhenFindByIdAndPlayerIdThenReturnPublication() {
        PublicationEntity publicationFound = this.publicationRepository.findByIdAndPlayerId(this.publication.getId(), this.user.getId());

        assertThat(publicationFound, is(this.publication));
    }

    @Test
    void givenPublicationOfAnotherUserWhenFindByIdAndPlayerIdThenReturnNull() {
        PublicationEntity publicationFound = this.publicationRepository.findByIdAndPlayerId(this.publication.getId(), this.user.getId() + 1);

        assertNull(publicationFound);
    }

    @Test
    void givenUserWithoutPublicationWhenFindByIdAndPlayerIdThenReturnNull() {
        PublicationEntity publicationFound = this.publicationRepository.findByIdAndPlayerId(this.publication.getId() + 1, this.user.getId());

        assertNull(publicationFound);
    }
}
