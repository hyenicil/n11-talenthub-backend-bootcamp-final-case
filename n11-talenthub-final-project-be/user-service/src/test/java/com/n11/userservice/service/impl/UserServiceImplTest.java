package com.n11.userservice.service.impl;

import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.dto.user.UserSaveRequest;
import com.n11.userservice.dto.user.UserUpdateRequest;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.enums.Gender;
import com.n11.userservice.entity.enums.Status;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserMapper mockUserMapper;

    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl(mockUserRepository, mockUserMapper);
    }

    @Test
    void shouldSaveUser() {

        // given
        UserSaveRequest request = new UserSaveRequest("name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        UserResponse expectedResult = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setSurname("surname");
        user.setBirthDate(LocalDate.of(2020, 1, 1));
        user.setStatus(Status.ACTIVE);

        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setStatus(Status.ACTIVE);

        UserResponse userResponse = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        // when
        when(mockUserMapper.mapUserSaveRequestToUser(new UserSaveRequest("name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE))).thenReturn(user);

        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        when(mockUserMapper.mapUserToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImplUnderTest.save(request);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetUserById() {

        // given
        UserResponse expectedResult = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setStatus(Status.ACTIVE);

        Optional<User> user = Optional.of(user1);

        UserResponse userResponse = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        // when
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockUserMapper.mapUserToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImplUnderTest.getById(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldGetAllUsers() {

        // given
        List<UserResponse> expectedResult = List.of(new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE));

        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setSurname("surname");
        user.setBirthDate(LocalDate.of(2020, 1, 1));
        user.setEmail("email");
        user.setStatus(Status.ACTIVE);

        List<User> users = List.of(user);

        // when
        when(mockUserRepository.findAll()).thenReturn(users);

        when(mockUserMapper.mapUserListToUserResponseList(users)).thenReturn(expectedResult);

        List<UserResponse> result = userServiceImplUnderTest.getAll();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldUpdateUser() {

        // given
        UserUpdateRequest request = new UserUpdateRequest("name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        UserResponse expectedResult = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setStatus(Status.ACTIVE);

        Optional<User> user = Optional.of(user1);

        User user2 = new User();
        user2.setId(0L);
        user2.setName("name");
        user2.setSurname("surname");
        user2.setBirthDate(LocalDate.of(2020, 1, 1));
        user2.setStatus(Status.ACTIVE);

        User user3 = new User();
        user3.setId(0L);
        user3.setName("name");
        user3.setSurname("surname");
        user3.setBirthDate(LocalDate.of(2020, 1, 1));
        user3.setStatus(Status.ACTIVE);

        UserResponse userResponse = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        // when
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockUserMapper.mapUserUpdateRequestToUser(any(User.class), eq(new UserUpdateRequest("name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE)))).thenReturn(user2);

        when(mockUserRepository.save(any(User.class))).thenReturn(user3);

        when(mockUserMapper.mapUserToUserResponse(any(User.class))).thenReturn(userResponse);
        UserResponse result = userServiceImplUnderTest.update(0L, request);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldDeleteUser() {

        // given
        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setStatus(Status.ACTIVE);

        Optional<User> user = Optional.of(user1);

        // when
        when(mockUserRepository.findById(0L)).thenReturn(user);

        userServiceImplUnderTest.delete(0L);

        // then
        verify(mockUserRepository).delete(any(User.class));
    }


    @Test
    void shouldDeactivateUser() {

        // given
        UserResponse expectedResult = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setStatus(Status.ACTIVE);

        Optional<User> user = Optional.of(user1);

        User user2 = new User();
        user2.setId(0L);
        user2.setName("name");
        user2.setSurname("surname");
        user2.setBirthDate(LocalDate.of(2020, 1, 1));
        user2.setStatus(Status.ACTIVE);

        UserResponse userResponse = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email", Gender.MALE);

        // when
        when(mockUserRepository.findById(0L)).thenReturn(user);

        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        when(mockUserMapper.mapUserToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImplUnderTest.deactivate(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetUserEntityById() {

        // given
        User user1 = new User();
        user1.setId(0L);
        user1.setName("name");
        user1.setSurname("surname");
        user1.setBirthDate(LocalDate.of(2020, 1, 1));
        user1.setStatus(Status.ACTIVE);

        // when
        when(mockUserRepository.findById(0L)).thenReturn(Optional.of(user1));
        User result = userServiceImplUnderTest.getUserById(0L);

        // then
        assertThat(result).isEqualTo(user1);
    }


}
