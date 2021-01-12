package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.user.UserDetailDto;
import com.hermanovich.accountingsystem.dto.user.UserDto;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class UserConverter {

    public UserDto convertUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .profile(ProfileConverter.convertProfileToProfileDto(user.getProfile()))
                .build();
    }

    public UserDetailDto convertUserToUserDetailDto(User user) {
        return UserDetailDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole().name())
                .profile(ProfileConverter.convertProfileToProfileDto(user.getProfile()))
                .build();
    }

    public User convertUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .role(UserRole.getRole(userDto.getRole()))
                .profile(ProfileConverter.convertProfileDtoToProfile(userDto.getProfile()))
                .build();
    }

    public List<UserDetailDto> convertUserListToUserDetailDtoList(List<User> userList) {
        List<UserDetailDto> userDetailDtoList = new ArrayList<>();
        for (User user : userList) {
            userDetailDtoList.add(convertUserToUserDetailDto(user));
        }
        return userDetailDtoList;
    }
}
