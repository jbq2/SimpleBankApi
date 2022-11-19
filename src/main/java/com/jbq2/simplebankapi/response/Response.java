package com.jbq2.simplebankapi.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public abstract class Response {
    private ResponseType responseType;
    private HttpStatus httpStatus;
    private String message;
    private Map<String, ?> data;
}