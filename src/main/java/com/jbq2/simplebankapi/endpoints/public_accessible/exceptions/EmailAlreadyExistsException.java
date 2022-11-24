package com.jbq2.simplebankapi.endpoints.public_accessible.exceptions;

public class EmailAlreadyExistsException extends CustomRegistrationException {
    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }

    public EmailAlreadyExistsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
