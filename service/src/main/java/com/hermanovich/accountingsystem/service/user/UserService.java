package com.hermanovich.accountingsystem.service.user;

import com.hermanovich.accountingsystem.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(User user);

    void removeUser(Integer id);

    void updateUser(User user);

    void updateUserPassword(String login, String password);

    User getUser(Integer id);

    User getUser(String login);

    List<User> getUserList();

    UserDetails loadUserByUsername(String login);

    UserDetails loadUserByUsernameAndPassword(String login, String password);
}
