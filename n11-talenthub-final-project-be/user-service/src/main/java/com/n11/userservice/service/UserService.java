package com.n11.userservice.service;

import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.dto.user.UserSaveRequest;
import com.n11.userservice.dto.user.UserUpdateRequest;
import com.n11.userservice.entity.User;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
public interface UserService {
    UserResponse save(UserSaveRequest request);
    UserResponse getById(Long id);
    List<UserResponse> getAll();
    UserResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
    UserResponse deactivate(Long id);
    User getUserById(Long id);
}
