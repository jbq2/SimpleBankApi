package com.jbq2.simplebankapi.endpoints.public_accessible.exceptions;

public class CustomRegistrationException extends RuntimeException {
    public CustomRegistrationException(String msg) {
        super(msg);
    }

    public CustomRegistrationException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
