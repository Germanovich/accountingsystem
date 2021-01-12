package com.hermanovich.accountingsystem.dto.car;

import com.hermanovich.accountingsystem.common.exception.ApplicationException;
import com.hermanovich.accountingsystem.util.MessageForUser;

public enum CarDtoStatus {

    AVAILABLE,
    MISSING;

    public static CarDtoStatus getStatus(String status) {
        if (status.isEmpty()) {
            throw new ApplicationException(MessageForUser.MESSAGE_FAILED_TO_GET_STATUS.get());
        }
        try {
            return CarDtoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(MessageForUser.MESSAGE_FAILED_TO_GET_STATUS.get(), e);
        }
    }
}
