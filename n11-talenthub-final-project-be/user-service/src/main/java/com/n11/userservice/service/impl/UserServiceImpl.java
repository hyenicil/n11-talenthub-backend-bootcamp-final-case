package com.n11.userservice.service.impl;

import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.dto.user.UserSaveRequest;
import com.n11.userservice.dto.user.UserUpdateRequest;
import com.n11.userservice.entity.User;
import com.n11.userservice.exception.UserNotFoundException;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.repository.UserRepository;
import com.n11.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.n11.userservice.common.error.ErrorMessages.*;
import static com.n11.userservice.entity.enums.Status.INACTIVE;

/**
 * @author Mehmet Akif Tanisik
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse save(UserSaveRequest request) {

        User user = userMapper.mapUserSaveRequestToUser(request);

        return userMapper.mapUserToUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getById(Long id) {

        User user = getUserById(id);

        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {

        List<User> userList = userRepository.findAll();

        return userMapper.mapUserListToUserResponseList(userList);
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {

        User user = getUserById(id);

        User updatedUser = userMapper.mapUserUpdateRequestToUser(user, request);

        return userMapper.mapUserToUserResponse(userRepository.save(updatedUser));
    }

    @Override
    public void delete(Long id) {

        User user = getUserById(id);

        userRepository.delete(user);
    }

    @Override
    public UserResponse deactivate(Long id) {

        User user = getUserById(id);
        user.setStatus(INACTIVE);

        return userMapper.mapUserToUserResponse(userRepository.save(user));
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
}
