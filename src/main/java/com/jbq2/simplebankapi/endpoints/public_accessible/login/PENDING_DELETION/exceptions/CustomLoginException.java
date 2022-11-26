package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;


public class CustomLoginException extends RuntimeException {
    public CustomLoginException(String msg) {
        super(msg);
    }

    public CustomLoginException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
