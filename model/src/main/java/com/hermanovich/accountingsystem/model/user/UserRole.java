package com.hermanovich.accountingsystem.model.user;

import com.hermanovich.accountingsystem.common.exception.ApplicationException;
import com.hermanovich.accountingsystem.util.MessageForUser;

public enum UserRole {

    ADMIN,
    USER;

    public static UserRole getRole(String role) {
        if (role.isEmpty()) {
            throw new ApplicationException(MessageForUser.MESSAGE_FAILED_TO_GET_ROLE.get());
        }
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(MessageForUser.MESSAGE_FAILED_TO_GET_ROLE.get(), e);
        }
    }
}
