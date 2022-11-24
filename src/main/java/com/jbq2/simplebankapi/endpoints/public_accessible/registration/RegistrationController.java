package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.CustomRegistrationException;
import com.jbq2.simplebankapi.endpoints.public_accessible.exceptions.ValidationException;
import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseBody
    public CustomResponse register(@RequestBody Registration registration){
        /* initializing some params of RegistrationResponse */
        ResponseType responseType = ResponseType.SUCCESS;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "";
        String registrationEmail = null;
        RuntimeException exception = null;

        try{
            /* validateAndSave() will throw an exception and will be handled in the catch clauses */
            registrationEmail = registrationService.validateAndSave(registration);
            httpStatus = HttpStatus.OK;
            message = "SUCCESS";
        }
        catch(ValidationException e){
            exception = e;
            responseType = ResponseType.ERROR;
            httpStatus = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }
        catch(CustomRegistrationException e){
            exception = e;
            responseType = ResponseType.ERROR;
            message = e.getMessage();
        }

        /* return appropriate Response */
        final String finalRegistrationEmail = registrationEmail;
        RuntimeException finalException = exception;
        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                new HashMap<>() {{
                    put("USER_EMAIL", finalRegistrationEmail);
                    put("EXCEPTION", finalException);
                }}
        );
    }
}