package com.hermanovich.accountingsystem.controller.authentication;

import com.hermanovich.accountingsystem.controller.config.Application;
import com.hermanovich.accountingsystem.controller.security.util.TokenHelper;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
import com.hermanovich.accountingsystem.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthenticationController.class)
@ContextConfiguration(classes = {Application.class})
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    TokenHelper tokenHelper;

    @Test
    void AuthenticationController_createAuthenticationToken() throws Exception {
        String token = "token";
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username("test")
                .password("111")
                .roles("ADMIN")
                .build();

        Mockito.doReturn(user).when(userService)
                .loadUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Mockito.doReturn(token).when(tokenHelper).generateToken(ArgumentMatchers.anyString());

        mockMvc.perform(RestDocumentationRequestBuilders.post("/authorization/login")
                .content("{\"login\": \"test\"," +
                        "\"password\": \"password\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void AuthenticationController_registerUser() throws Exception {
        User user = User.builder()
                .id(1)
                .login("test")
                .password("111")
                .profile(Profile.builder()
                        .name("Test")
                        .surname("Test")
                        .build())
                .role(UserRole.USER)
                .build();

        Mockito.doNothing().when(userService).addUser(user);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/authorization/registration")
                .content("{\"login\": \"Admin2\"," +
                        "    \"password\": \"test\"," +
                        "    \"profile\": {" +
                        "        \"name\": \"Bishop\"," +
                        "        \"surname\": \"Carey\"," +
                        "        \"sex\": 1\n" +
                        "    }," +
                        "    \"role\": \"AdmIn\"" +
                        "    }}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}