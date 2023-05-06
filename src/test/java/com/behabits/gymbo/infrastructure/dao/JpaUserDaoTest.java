package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.ExistingUserException;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.infrastructure.repository.UserRepository;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaUserDaoTest {

    @InjectMocks
    private JpaUserDao jpaUserDao;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    private final UserEntityRepository userEntityRepository = new UserEntityRepository();

    private final UserModelRepository userModelRepository = new UserModelRepository();

    @Test
    void givenNonExistentUserWhenCreateUserThenReturnUser() {
        User user = this.userModelRepository.getUser();
        UserEntity userEntity = this.userEntityRepository.getUser();

        when(this.userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(this.userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(this.userRepository.save(userEntity)).thenReturn(userEntity);
        when(this.userEntityMapper.toDomain(userEntity)).thenReturn(user);

        assertThat(this.jpaUserDao.createUser(user), is(user));
    }

    @Test
    void givenExistentUserWhenCreateUserThenThrowExistingUsernameException() {
        User user = this.userModelRepository.getUser();
        UserEntity userEntity = this.userEntityRepository.getUser();

        when(this.userRepository.findByUsername(user.getUsername())).thenReturn(userEntity);

        assertThrows(ExistingUserException.class, () -> this.jpaUserDao.createUser(user));
    }

    @Test
    void givenExistentUserWhenFindByUsernameThenReturnUser() {
        User user = this.userModelRepository.getUser();
        UserEntity userEntity = this.userEntityRepository.getUser();

        when(this.userRepository.findByUsername(user.getUsername())).thenReturn(userEntity);
        when(this.userEntityMapper.toDomain(userEntity)).thenReturn(user);

        assertThat(this.jpaUserDao.findByUsername(user.getUsername()), is(user));
    }

    @Test
    void givenNonExistentUserWhenFindByUsernameThenReturnNull() {
        String nonExistentUsername = "nonExistentUsername";

        when(this.userRepository.findByUsername(nonExistentUsername)).thenReturn(null);

        assertThat(this.jpaUserDao.findByUsername(nonExistentUsername), is(nullValue()));
    }
}
