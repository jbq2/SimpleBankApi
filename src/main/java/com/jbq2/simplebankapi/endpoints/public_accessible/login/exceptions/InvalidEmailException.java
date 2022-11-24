package com.jbq2.simplebankapi.endpoints.public_accessible.login.exceptions;

public class InvalidEmailException extends CustomLoginException {

    public InvalidEmailException(String msg){
        super(msg);
    }

    public InvalidEmailException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
