package com.jbq2.simplebankapi.endpoints.public_accessible.login.PENDING_DELETION.exceptions;

public class EmailNotFoundException extends CustomLoginException {
    public EmailNotFoundException(String msg) {
        super(msg);
    }

    public EmailNotFoundException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
