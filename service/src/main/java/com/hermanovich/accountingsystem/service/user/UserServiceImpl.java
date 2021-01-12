package com.hermanovich.accountingsystem.service.user;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.dao.order.OrderDao;
import com.hermanovich.accountingsystem.dao.profile.ProfileDao;
import com.hermanovich.accountingsystem.dao.request.RequestDao;
import com.hermanovich.accountingsystem.dao.setting.SettingDao;
import com.hermanovich.accountingsystem.dao.user.UserDao;
import com.hermanovich.accountingsystem.model.profile.Profile;
import com.hermanovich.accountingsystem.model.user.User;
import com.hermanovich.accountingsystem.model.user.UserRole;
import com.hermanovich.accountingsystem.util.MessageForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Value("${setting.configurationNameForCreationUser:userFunctionEnabled}")
    private String configurationNameForCreationUser;
    @Value("${role.user:USER}")
    private String defaultUserRole;
    private final UserDao userDao;
    private final RequestDao requestDao;
    private final OrderDao orderDao;
    private final ProfileDao profileDao;
    private final SettingDao settingDao;

    @Transactional
    @Override
    public void addUser(User user) {
        if (Boolean.FALSE.equals(settingDao.getSettingByName(configurationNameForCreationUser).getAccess())) {
            throw new BusinessException(MessageForUser.MESSAGE_USER_CREATION_IS_PROHIBITED.get());
        }
        verifyUserData(user);
        if (userDao.getByLogin(user.getLogin()) == null) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.getProfile().setDateOfRegistration(new Date());
            userDao.save(user);
            return;
        }
        throw new BusinessException(MessageForUser.MESSAGE_THIS_LOGIN_ALREADY_EXISTS.get());
    }

    @Transactional
    @Override
    public void removeUser(Integer id) {
        if (requestDao.getListByUserId(id).isEmpty() && orderDao.getListByUserId(id).isEmpty()) {
            userDao.delete(id);
            profileDao.delete(id);
            return;
        }
        throw new BusinessException(MessageForUser.MESSAGE_USER_HAS_ORDERS_OR_REQUESTS.get());
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        verifyUserData(user);

        if (userDao.getByLogin(user.getLogin()) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_USER_IS_NOT_FOUND.get());
        }

        if (user.getRole() == null) {
            user.setRole(UserRole.getRole(defaultUserRole));
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.update(user);
    }

    @Transactional
    @Override
    public void updateUserPassword(String login, String password) {
        if (password == null || password.isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_PASSWORD_IS_MISSING.get());
        }
        User user = userDao.getByLogin(login);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userDao.update(user);
    }

    @Transactional
    @Override
    public User getUser(Integer id) {
        User user = userDao.getById(id);
        if (user == null) {
            throw new BusinessException(MessageForUser.MESSAGE_NO_SUCH_USER_EXISTS.get());
        }
        return user;
    }

    @Transactional
    @Override
    public User getUser(String login) {
        return userDao.getByLogin(login);
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return userDao.getAll();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsernameAndPassword(String username, String password) {
        User user = userDao.getByLogin(username);
        if (user != null && isPasswordMatches(password, user.getPassword())) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        }
        throw new UsernameNotFoundException(MessageForUser.MESSAGE_USER_IS_NOT_FOUND.get());
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.getByLogin(username);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        }
        throw new UsernameNotFoundException(MessageForUser.MESSAGE_USER_IS_NOT_FOUND.get());
    }

    private Boolean isPasswordMatches(String password, String userPassword) {
        return new BCryptPasswordEncoder().matches(password, userPassword);
    }

    private void verifyUserData(User user) {
        verifyProfileData(user.getProfile());
        if (user.getLogin() == null ||
                user.getPassword() == null ||
                user.getRole() == null ||
                user.getLogin().isEmpty() ||
                user.getPassword().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_USER_DATA.get());
        }
    }

    private void verifyProfileData(Profile profile) {
        if (profile == null ||
                profile.getName() == null ||
                profile.getSurname() == null ||
                profile.getName().isEmpty() ||
                profile.getSurname().isEmpty()) {
            throw new BusinessException(MessageForUser.MESSAGE_NOT_FOUND_ALL_PROFILE_DATA.get());
        }
    }
}
