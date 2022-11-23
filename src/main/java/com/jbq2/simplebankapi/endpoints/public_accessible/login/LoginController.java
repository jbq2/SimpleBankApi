package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public CustomResponse loginUser(@RequestBody Login login){
        /* validating login form, returns a specific LoginStatus */
        LoginStatus loginStatus = loginService.validateLogin(login);

        /* initializing response params */
        ResponseType responseType = ResponseType.ERROR;
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        String message = "";

        /* customizing parameters based on returned loginStatus */
        switch(loginStatus){
            case SUCCESS -> {
                responseType = ResponseType.SUCCESS;
                httpStatus = HttpStatus.OK;
                message = "SUCCESS";
            }
            case FAIL_BAD_EMAIL -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Invalid email format.";
            }
            case FAIL_BAD_PASSWORD -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Invalid password format.";
            }
            case FAIL_EMAIL_NOT_EXIST -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "Email was not found.";
            }
            case FAIL_INCORRECT_PASSWORD -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Incorrect password.";
            }
        }

        /* return Response */
        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                new HashMap<>() {{
                    put("loginStatus", loginStatus);
                    put("loginEmail", login.getEmail());
                }}
        );
    }
}
