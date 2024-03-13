package com.n11.userservice.mapper;

import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.dto.user.UserSaveRequest;
import com.n11.userservice.dto.user.UserUpdateRequest;
import com.n11.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    User mapUserSaveRequestToUser(UserSaveRequest request);

    UserResponse mapUserToUserResponse(User user);

    List<UserResponse> mapUserListToUserResponseList(List<User> userList);

    User mapUserUpdateRequestToUser(@MappingTarget User user, UserUpdateRequest request);
}
