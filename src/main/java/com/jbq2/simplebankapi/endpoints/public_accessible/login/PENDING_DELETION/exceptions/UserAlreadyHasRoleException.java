package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;

public class UserAlreadyHasRoleException extends CustomRegistrationException {
    public UserAlreadyHasRoleException(String msg) {
        super(msg);
    }

    public UserAlreadyHasRoleException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
