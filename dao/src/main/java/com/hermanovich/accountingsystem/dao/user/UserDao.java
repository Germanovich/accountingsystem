package com.hermanovich.accountingsystem.dao.user;

import com.hermanovich.accountingsystem.dao.GenericDao;
import com.hermanovich.accountingsystem.model.user.User;

public interface UserDao extends GenericDao<User, Integer> {

    User getByLogin(String login);
}
