package com.jbq2.simplebankapi.registration;

import com.jbq2.simplebankapi.response.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private ResponseType responseType;
    private HttpStatus httpStatus;
    private RegistrationStatus registrationStatus;
    private String message;
    private Map<String, ?> data;
}
