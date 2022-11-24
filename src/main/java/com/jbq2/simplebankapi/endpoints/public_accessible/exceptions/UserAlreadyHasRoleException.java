package com.jbq2.simplebankapi.endpoints.public_accessible.exceptions;

public class UserAlreadyHasRoleException extends CustomRegistrationException {
    public UserAlreadyHasRoleException(String msg) {
        super(msg);
    }

    public UserAlreadyHasRoleException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
