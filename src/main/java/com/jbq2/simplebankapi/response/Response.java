package com.jbq2.simplebankapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@AllArgsConstructor
public class Response {
    private ResponseType responseType;
    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
    private Map<String, ?> body;
}
