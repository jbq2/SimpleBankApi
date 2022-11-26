package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;

public class EmailAlreadyExistsException extends CustomRegistrationException {
    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }

    public EmailAlreadyExistsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
