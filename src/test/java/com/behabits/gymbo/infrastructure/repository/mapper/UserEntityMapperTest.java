package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserEntityMapperTest {

    @Autowired
    private UserEntityMapper mapper;

    private final UserEntityRepository userEntityRepository = new UserEntityRepository();

    private final UserModelRepository userModelRepository = new UserModelRepository();

    @Test
    void givenUserWhenMapToEntityThenReturnUserEntity() {
        User user = this.userModelRepository.getUser();

        UserEntity userEntity = this.mapper.toEntity(user);

        assertThat(userEntity.getId(), is(user.getId()));
        assertThat(userEntity.getUsername(), is(user.getUsername()));
        assertThat(userEntity.getPassword(), is(user.getPassword()));
        assertThat(userEntity.getFirstName(), is(user.getFirstName()));
        assertThat(userEntity.getLastName(), is(user.getLastName()));
        assertThat(userEntity.getEmail(), is(user.getEmail()));
        assertThat(userEntity.getSports(), is(user.getSports()));
    }

    @Test
    void givenUserEntityWhenMapToDomainThenReturnUser() {
        UserEntity userEntity = this.userEntityRepository.getUser();

        User user = this.mapper.toDomain(userEntity);

        assertThat(user.getId(), is(userEntity.getId()));
        assertThat(user.getUsername(), is(userEntity.getUsername()));
        assertThat(user.getPassword(), is(userEntity.getPassword()));
        assertThat(user.getFirstName(), is(userEntity.getFirstName()));
        assertThat(user.getLastName(), is(userEntity.getLastName()));
        assertThat(user.getEmail(), is(userEntity.getEmail()));
        assertThat(user.getSports(), is(userEntity.getSports()));
    }
}
