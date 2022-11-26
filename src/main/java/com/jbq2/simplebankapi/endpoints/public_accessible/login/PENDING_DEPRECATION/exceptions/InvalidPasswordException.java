package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DEPRECATION.exceptions;

public class InvalidPasswordException extends ValidationException {
    public InvalidPasswordException(String msg){
        super(msg);
    }

    public InvalidPasswordException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}

