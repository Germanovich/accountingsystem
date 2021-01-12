package com.hermanovich.accountingsystem.controller.authentication;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.service.security.repository.TokenRepository;
import com.hermanovich.accountingsystem.dto.token.AuthenticationResponse;
import com.hermanovich.accountingsystem.controller.security.util.AuthenticationHelper;
import com.hermanovich.accountingsystem.controller.security.util.TokenHelper;
import com.hermanovich.accountingsystem.controller.util.UserConverter;
import com.hermanovich.accountingsystem.dto.user.UserDto;
import com.hermanovich.accountingsystem.service.user.UserService;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authorization")
@CrossOrigin
public class AuthenticationController {

    @Value("${role.default:USER}")
    private String defaultRole;
    private final UserService userService;
    private final TokenHelper tokenHelper;
    private final TokenRepository tokenRepository;

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody UserDto userDto) {
        UserDetails userDetails = userService.loadUserByUsernameAndPassword(userDto.getLogin(), userDto.getPassword());

        String token = new AuthenticationResponse(tokenHelper.generateToken(userDetails.getUsername())).getToken();
        tokenRepository.addToken(userDto.getLogin(), token);
        return new AuthenticationResponse(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> removeAuthenticationToken() {
        tokenRepository.removeToken(AuthenticationHelper.getAuthenticationUserName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto userDto) {
        if (userDto == null || userDto.getProfile() == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_USER_DATA.get());
        }
        userDto.setRole(defaultRole);
        userService.addUser(UserConverter.convertUserDtoToUser(userDto));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
