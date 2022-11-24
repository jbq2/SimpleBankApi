package com.jbq2.simplebankapi.endpoints.public_accessible.login.exceptions;

public class InvalidPasswordException extends CustomLoginException {
    public InvalidPasswordException(String msg){
        super(msg);
    }

    public InvalidPasswordException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}

