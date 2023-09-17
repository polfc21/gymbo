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

import java.util.List;

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

    private final User user = new UserModelRepository().getUser();

    private final UserEntity userEntity = new UserEntityRepository().getUser();

    @Test
    void givenUserWhenSaveUserThenReturnUser() {
        when(this.userEntityMapper.toEntity(this.user)).thenReturn(this.userEntity);
        when(this.userRepository.save(this.userEntity)).thenReturn(this.userEntity);
        when(this.userEntityMapper.toDomain(this.userEntity)).thenReturn(this.user);

        assertThat(this.jpaUserDao.saveUser(this.user), is(this.user));
    }

    @Test
    void givenExistentUserWhenFindByUsernameThenReturnUser() {
        when(this.userRepository.findByUsername(this.user.getUsername())).thenReturn(this.userEntity);
        when(this.userEntityMapper.toDomain(this.userEntity)).thenReturn(this.user);

        assertThat(this.jpaUserDao.findByUsername(this.user.getUsername()), is(this.user));
    }

    @Test
    void givenNonExistentUserWhenFindByUsernameThenReturnNull() {
        String nonExistentUsername = "nonExistentUsername";

        when(this.userRepository.findByUsername(nonExistentUsername)).thenReturn(null);

        assertThat(this.jpaUserDao.findByUsername(nonExistentUsername), is(nullValue()));
    }

    @Test
    void givenKilometersAndLoggedUserIdWhenFindUsersInKilometersOrderedByDistanceThenReturnUsers() {
        double kilometers = 1.0;
        long loggedUserId = 1L;
        User nearUser = new UserModelRepository().getReviewed();
        UserEntity nearUserEntity = new UserEntityRepository().getReviewed();

        when(this.userRepository.findUsersInKilometersOrderedByDistanceFromPlayerId(loggedUserId, kilometers)).thenReturn(List.of(nearUserEntity));
        when(this.userEntityMapper.toDomain(nearUserEntity)).thenReturn(nearUser);

        assertThat(this.jpaUserDao.findUsersInKilometersOrderedByDistanceFromLoggedUser(loggedUserId, kilometers), is(List.of(nearUser)));
    }

    @Test
    void whenFindAllThenReturnUsers() {
        UserEntity userEntity = new UserEntityRepository().getUser();
        User user = new UserModelRepository().getUser();

        when(this.userRepository.findAll()).thenReturn(List.of(userEntity));
        when(this.userEntityMapper.toDomain(userEntity)).thenReturn(user);

        assertThat(this.jpaUserDao.findAll(), is(List.of(user)));
    }

}
