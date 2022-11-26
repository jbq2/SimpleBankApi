package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;

public class IncorrectPasswordException extends CustomLoginException {
    public IncorrectPasswordException(String msg){
        super(msg);
    }

    public IncorrectPasswordException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
