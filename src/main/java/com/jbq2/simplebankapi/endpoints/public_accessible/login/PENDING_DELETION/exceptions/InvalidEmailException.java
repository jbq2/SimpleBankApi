package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;

public class InvalidEmailException extends ValidationException {

    public InvalidEmailException(String msg){
        super(msg);
    }

    public InvalidEmailException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
