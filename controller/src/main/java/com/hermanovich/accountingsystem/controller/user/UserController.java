package com.hermanovich.accountingsystem.controller.user;

import com.hermanovich.accountingsystem.controller.security.util.AuthenticationHelper;
import com.hermanovich.accountingsystem.controller.util.UserConverter;
import com.hermanovich.accountingsystem.dto.user.UserDetailDto;
import com.hermanovich.accountingsystem.dto.user.UserDto;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.service.user.UserService;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto) {
        if (userDto == null || userDto.getRole() == null || userDto.getProfile() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_USER_DATA.get());
        }
        userService.addUser(UserConverter.convertUserDtoToUser(userDto));
        log.info("User added");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        log.info("User deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(UserConverter.convertUserDtoToUser(userDto));
        log.info("User updated");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/password")
    public ResponseEntity<Void> updateUserPassword(@RequestBody UserDto userDto) {
        userService.updateUserPassword(AuthenticationHelper.getAuthenticationUserName(), userDto.getPassword());
        log.info("Password updated");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDetailDto getUser(@PathVariable Integer id) {
        return UserConverter.convertUserToUserDetailDto(userService.getUser(id));
    }

    @GetMapping("/info")
    public UserDetailDto getUserInfo() {
        return UserConverter.convertUserToUserDetailDto(
                userService.getUser(AuthenticationHelper.getAuthenticationUserName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDetailDto> getUserList() {
        return UserConverter.convertUserListToUserDetailDtoList(userService.getUserList());
    }
}
