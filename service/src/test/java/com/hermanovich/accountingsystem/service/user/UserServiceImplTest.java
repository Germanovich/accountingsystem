package com.hermanovich.accountingsystem.service.user;

import com.hermanovich.accountingsystem.dao.order.OrderDao;
import com.hermanovich.accountingsystem.dao.profile.ProfileDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.dao.user.UserDao;
import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.model.order.Order;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.request.Request;
import com.hermanovich.accountingsystem.model.setting.Setting;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserDao userDao;
    @Mock
    RequestDao requestDao;
    @Mock
    OrderDao orderDao;
    @Mock
    ProfileDao profileDao;
    @Mock
    SettingDao settingDao;

    @Test
    void UserServiceImpl_addUser() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .login("test")
                .role(UserRole.USER)
                .password("test123")
                .profile(profile)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(userService,
                "configurationNameForCreationUser",
                "userFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(null).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(userDao).save(user);

        userService.addUser(user);
        Mockito.verify(userDao).save(user);
    }

    @Test
    void UserServiceImpl_addUser_whenUserCreationBlockedToThrowBusinessException() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .login("test")
                .role(UserRole.USER)
                .password("test123")
                .profile(profile)
                .build();
        Setting setting = Setting.builder().access(Boolean.FALSE).build();

        ReflectionTestUtils.setField(userService,
                "configurationNameForCreationUser",
                "userFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.addUser(user));
    }

    @Test
    void UserServiceImpl_addUser_whenUserHasEmptyLoginToThrowBusinessException() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("")
                .password("test123")
                .profile(profile)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(userService,
                "configurationNameForCreationUser",
                "userFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.addUser(user));
    }

    @Test
    void UserServiceImpl_addUser_whenUserHasEmptyPasswordToThrowBusinessException() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .password("")
                .login("test")
                .profile(profile)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(userService,
                "configurationNameForCreationUser",
                "userFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.addUser(user));
    }

    @Test
    void UserServiceImpl_addUser_whenUserHasEmptyProfileToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test")
                .password("111")
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(userService,
                "configurationNameForCreationUser",
                "userFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.addUser(user));
    }

    @Test
    void UserServiceImpl_addUser_whenLoginAlreadyExistsToThrowBusinessException() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .login("test")
                .role(UserRole.USER)
                .password("test123")
                .profile(profile)
                .build();
        Setting setting = Setting.builder().access(Boolean.TRUE).build();

        ReflectionTestUtils.setField(userService,
                "configurationNameForCreationUser",
                "userFunctionEnabled");

        Mockito.doReturn(setting).when(settingDao).getSettingByName(ArgumentMatchers.anyString());
        Mockito.doReturn(user).when(userDao).getByLogin(user.getLogin());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.addUser(user));
    }

    @Test
    void UserServiceImpl_removeUser() {
        List<Order> orderList = new ArrayList<>();
        List<Request> requestList = new ArrayList<>();

        Mockito.doReturn(requestList).when(requestDao).getListByUserId(ArgumentMatchers.anyInt());
        Mockito.doReturn(orderList).when(orderDao).getListByUserId(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(userDao).delete(ArgumentMatchers.anyInt());
        Mockito.doNothing().when(profileDao).delete(ArgumentMatchers.anyInt());

        userService.removeUser(ArgumentMatchers.anyInt());
        Mockito.verify(userDao).delete(ArgumentMatchers.anyInt());
        Mockito.verify(profileDao).delete(ArgumentMatchers.anyInt());
    }

    @Test
    void UserServiceImpl_removeUser_whenUserHasOrdersToThrowBusinessException() {
        Integer userId = 1;
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.builder().build());
        List<Request> requestList = new ArrayList<>();

        Mockito.doReturn(requestList).when(requestDao).getListByUserId(ArgumentMatchers.anyInt());
        Mockito.doReturn(orderList).when(orderDao).getListByUserId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.removeUser(userId));
    }

    @Test
    void UserServiceImpl_removeUser_whenUserHasRequestsToThrowBusinessException() {
        Integer userId = 1;
        List<Request> requestList = new ArrayList<>();
        requestList.add(Request.builder().build());

        Mockito.doReturn(requestList).when(requestDao).getListByUserId(ArgumentMatchers.anyInt());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.removeUser(userId));
    }

    @Test
    void UserServiceImpl_updateUser() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test")
                .password("test111")
                .profile(profile)
                .build();

        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(userDao).update(user);

        userService.updateUser(user);
        Mockito.verify(userDao).update(user);
    }

    @Test
    void UserServiceImpl_updateUser_whenUserHasEmptyLoginToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("")
                .password("test123")
                .profile(Profile.builder().build())
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> userService.updateUser(user));
    }

    @Test
    void UserServiceImpl_updateUser_whenUserHasEmptyPasswordToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test")
                .password("")
                .profile(Profile.builder().build())
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> userService.updateUser(user));
    }

    @Test
    void UserServiceImpl_updateUser_whenUserHasEmptyProfileToThrowBusinessException() {
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test")
                .password("111")
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> userService.updateUser(user));
    }

    @Test
    void UserServiceImpl_updateUser_whenUserNotFoundToThrowBusinessException() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .login("test")
                .role(UserRole.USER)
                .password("test123")
                .profile(profile)
                .build();

        Mockito.doReturn(null).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(BusinessException.class,
                () -> userService.updateUser(user));
    }

    @Test
    void UserServiceImpl_updateUserPassword() {
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("user")
                .password("test111")
                .profile(profile)
                .build();

        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());
        Mockito.doNothing().when(userDao).update(user);

        userService.updateUserPassword(user.getLogin(), user.getPassword());
        Mockito.verify(userDao).update(user);
    }

    @Test
    void UserServiceImpl_updateUserPassword_whenUserHasEmptyPasswordToThrowBusinessException() {
        String userLogin = "1";
        String password = "";
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();

        Assertions.assertThrows(BusinessException.class,
                () -> userService.updateUserPassword(userLogin, password));
    }

    @Test
    void UserServiceImpl_getUser() {
        Integer userId = 1;
        Profile profile = Profile.builder()
                .name("Dima")
                .surname("German")
                .build();
        User user = User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test")
                .password("test111")
                .profile(profile)
                .build();

        Mockito.doReturn(user).when(userDao).getById(ArgumentMatchers.anyInt());

        userService.getUser(userId);

        Mockito.verify(userDao).getById(ArgumentMatchers.anyInt());
    }

    @Test
    void UserServiceImpl_getUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(User.builder()
                .id(2)
                .role(UserRole.USER)
                .login("test")
                .password("test111")
                .profile(Profile.builder().build())
                .build());
        userList.add(User.builder()
                .id(1)
                .role(UserRole.USER)
                .login("test2")
                .password("test121")
                .profile(Profile.builder().build())
                .build());
        userList.add(User.builder()
                .id(3)
                .role(UserRole.USER)
                .login("test3")
                .password("test131")
                .profile(Profile.builder().build())
                .build());

        Mockito.doReturn(userList).when(userDao).getAll();

        List<User> users = userService.getUserList();


        Assertions.assertNotNull(users);
        for (User user : users) {
            Assertions.assertNotNull(user);
        }
        Mockito.verify(userDao).getAll();
    }

    @Test
    void UserServiceImpl_loadUserByUsernameAndPassword() {
        String login = "test";
        String password = "111";
        User user = User.builder()
                .id(1)
                .login(login)
                .password(new BCryptPasswordEncoder().encode(password))
                .profile(Profile.builder().build())
                .role(UserRole.USER)
                .build();

        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());

        UserDetails userDetails = userService.loadUserByUsernameAndPassword(login, password);
        Mockito.verify(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertEquals(userDetails.getUsername(), user.getLogin());
        Assertions.assertEquals(userDetails.getPassword(), user.getPassword());
    }

    @Test
    void UserServiceImpl_loadUserByUsernameAndPassword_whenUserNotFoundToThrowBusinessException() {
        String login = "test";
        String password = "111";

        Mockito.doReturn(null).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsernameAndPassword(login, password));
    }

    @Test
    void UserServiceImpl_loadUserByUsername() {
        String login = "test";
        User user = User.builder()
                .id(1)
                .login(login)
                .password("password")
                .profile(Profile.builder().build())
                .role(UserRole.USER)
                .build();

        Mockito.doReturn(user).when(userDao).getByLogin(ArgumentMatchers.anyString());

        UserDetails userDetails = userService.loadUserByUsername(login);
        Mockito.verify(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertEquals(userDetails.getUsername(), user.getLogin());
    }

    @Test
    void UserServiceImpl_loadUserByUsername_whenUserNotFoundToThrowBusinessException() {
        String login = "test";

        Mockito.doReturn(null).when(userDao).getByLogin(ArgumentMatchers.anyString());

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(login));
    }
}