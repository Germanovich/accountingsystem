package com.hermanovich.accountingsystem.model.car;

import com.hermanovich.accountingsystem.common.exception.ApplicationException;
import com.hermanovich.accountingsystem.util.MessageForUser;

public enum CarStatus {

    AVAILABLE,
    MISSING;

    public static CarStatus getStatus(String status) {
        if (status.isEmpty()) {
            throw new ApplicationException(MessageForUser.MESSAGE_FAILED_TO_GET_STATUS.get());
        }
        try {
            return CarStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(MessageForUser.MESSAGE_FAILED_TO_GET_STATUS.get(), e);
        }
    }
}
