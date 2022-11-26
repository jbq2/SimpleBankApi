package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DEPRECATION.exceptions;

public class NonMatchingPasswordsException extends ValidationException {
    public NonMatchingPasswordsException(String msg) {
        super(msg);
    }

    public NonMatchingPasswordsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
