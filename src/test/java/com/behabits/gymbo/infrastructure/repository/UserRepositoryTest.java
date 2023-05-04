package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenUsernameWhenFindByUsernameThenReturnUser() {
        UserEntity user = new UserEntityRepository().getUser();
        user.setId(null);
        this.entityManager.persist(user);

        assertThat(this.userRepository.findByUsername(user.getUsername()), is(user));
    }

    @Test
    void givenNullUsernameWhenFindByUsernameThenReturnNull() {
        assertNull(this.userRepository.findByUsername("null"));
    }

}
