package com.jbq2.simplebankapi.registration;

import com.jbq2.simplebankapi.response.Response;
import com.jbq2.simplebankapi.response.ResponseType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/userRegistration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestBody Registration registration){
        /* RegistrationStatus is returned */
        RegistrationStatus registrationStatus = registrationService.validateAndSave(registration);

        /* initializing some params of RegistrationResponse */
        ResponseType responseType = ResponseType.ERROR;
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        String message = "";

        /* set the parameters based on the registrationStatus */
        switch(registrationStatus){
            case SUCCESS -> {
                responseType = ResponseType.SUCCESS;
                httpStatus = HttpStatus.OK;
                message = "Registered Successfully.";
            }
            case FAIL_BAD_ROLE_SAVE -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "User-role combination already exists.";
            }
            case FAIL_BAD_EMAIL -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Email format is invalid.";
            }
            case FAIL_BAD_MATCH -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Passwords do not match.";
            }
            case FAIL_BAD_PASSWORD -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Password must be at least 8 characters long and must contain a special character";
            }
            case FAIL_EMAIL_EXISTS -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "Email already exists.";
            }
        }

        /* return appropriate Response */
        return new Response(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                new HashMap<>() {{
                    put("registrationStatus", registrationStatus);
                    put("registrationForm", registration);
                }}
        );
    }
}
