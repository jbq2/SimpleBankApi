package com.jbq2.simplebankapi.endpoints.public_accessible.login.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmailNotFoundException extends CustomLoginException {
    public EmailNotFoundException(String msg) {
        super(msg);
    }

    public EmailNotFoundException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
