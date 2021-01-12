package com.hermanovich.accountingsystem.controller.user;

import com.hermanovich.accountingsystem.controller.config.Application;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {Application.class})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void UserController_addUser() throws Exception {
        User user = User.builder()
                .id(1)
                .login("Admin2")
                .role(UserRole.USER)
                .password("test")
                .profile(Profile.builder()
                        .name("Bishop")
                        .surname("Carey")
                        .sex(Boolean.TRUE)
                        .build())
                .build();

        Mockito.doNothing().when(userService).addUser(user);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/users")
                .content("{\"login\": \"Admin2\"," +
                        "    \"password\": \"test\"," +
                        "    \"profile\": {\n" +
                        "        \"name\": \"Bishop\"," +
                        "        \"surname\": \"Carey\"," +
                        "        \"sex\": 1}," +
                        "    \"role\": \"AdmIn\"}}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void UserController_removeUser() throws Exception {
        Integer id = 10;

        Mockito.doNothing().when(userService).removeUser(id);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/users/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void UserController_updateUser() throws Exception {
        User user = User.builder()
                .id(1)
                .login("Admin2")
                .role(UserRole.USER)
                .password("test")
                .profile(Profile.builder()
                        .name("Bishop")
                        .surname("Carey")
                        .sex(Boolean.TRUE)
                        .build())
                .build();

        Mockito.doNothing().when(userService).updateUser(user);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/users")
                .content("{\"login\": \"Admin2\"," +
                        "    \"password\": \"test\"," +
                        "    \"profile\": {\n" +
                        "        \"name\": \"Bishop\"," +
                        "        \"surname\": \"Carey\"," +
                        "        \"sex\": 1}," +
                        "    \"role\": \"AdmIn\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void UserController_updateUserPassword() throws Exception {
        User user = User.builder()
                .id(1)
                .login("user111")
                .password("test")
                .build();

        Mockito.doNothing().when(userService).updateUserPassword(user.getLogin(), user.getPassword());

        mockMvc.perform(RestDocumentationRequestBuilders.put("/users/update/password")
                .content("{\"password\": \"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void UserController_getUser() throws Exception {
        Integer userId = 1;
        User user = User.builder()
                .id(userId)
                .login("Admin2")
                .role(UserRole.USER)
                .password("test")
                .profile(Profile.builder()
                        .name("Bishop")
                        .surname("Carey")
                        .sex(Boolean.TRUE)
                        .build())
                .build();

        Mockito.doReturn(user).when(userService).getUser(userId);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{id}", userId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void UserController_getUserInfo() throws Exception {
        User user = User.builder()
                .id(1)
                .login("user")
                .role(UserRole.USER)
                .password("test")
                .profile(Profile.builder()
                        .name("Bishop")
                        .surname("Carey")
                        .sex(Boolean.TRUE)
                        .build())
                .build();

        Mockito.doReturn(user).when(userService).getUser(ArgumentMatchers.anyString());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/info")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void UserController_getUserList() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(1)
                .login("Admin2")
                .role(UserRole.USER)
                .password("test")
                .profile(Profile.builder()
                        .name("Bishop")
                        .surname("Carey")
                        .sex(Boolean.TRUE)
                        .build())
                .build());
        users.add(User.builder()
                .id(2)
                .login("Admin34")
                .role(UserRole.USER)
                .password("test123")
                .profile(Profile.builder()
                        .name("Bishop")
                        .surname("Carey")
                        .sex(Boolean.TRUE)
                        .build())
                .build());

        Mockito.doReturn(users).when(userService).getUserList();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/users")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}