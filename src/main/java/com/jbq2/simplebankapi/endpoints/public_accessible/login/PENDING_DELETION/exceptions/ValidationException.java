package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
