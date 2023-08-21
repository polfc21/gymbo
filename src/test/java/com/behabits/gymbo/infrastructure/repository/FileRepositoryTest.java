package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.FileEntityRepository;
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
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();

    private final FileEntity file = new FileEntityRepository().getFile();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.file.setId(null);
        this.file.setPlayer(this.player);
        this.file.setPublication(null);
        this.entityManager.persist(this.file);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.file);
        this.entityManager.remove(this.player);
    }

    @Test
    void givenFileOfUserWhenFindByIdAndPlayerIdThenReturnFile() {
        FileEntity file = this.fileRepository.findByIdAndPlayerId(this.file.getId(), this.player.getId());

        assertThat(file, is(this.file));
    }


    @Test
    void givenFileOfUserWhenFindByIdAndPlayerIdThenReturnNull() {
        FileEntity file = this.fileRepository.findByIdAndPlayerId(this.file.getId() + 1, this.player.getId());

        assertNull(file);
    }
}
