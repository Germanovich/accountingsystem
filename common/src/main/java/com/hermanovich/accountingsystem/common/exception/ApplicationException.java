package com.hermanovich.accountingsystem.common.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Exception ex) {
        super(message + ":\n" + ex);
    }
}
