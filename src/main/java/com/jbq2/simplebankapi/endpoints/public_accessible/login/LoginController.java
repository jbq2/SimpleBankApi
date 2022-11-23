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
        String loginMessage = "";

        /* customizing parameters based on returned loginStatus */
        switch(loginStatus){
            case SUCCESS -> {
                responseType = ResponseType.SUCCESS;
                httpStatus = HttpStatus.OK;
                message = "SUCCESS";
                loginMessage = "Login attempt was successful";
            }
            case FAIL_BAD_EMAIL -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Invalid email format.";
                loginMessage = "Login attempt failed; likely due to the regex rejecting the entered email";
            }
            case FAIL_BAD_PASSWORD -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Invalid password format.";
                loginMessage = "Login attempt failed; likely due to the regex rejecting the entered password";
            }
            case FAIL_EMAIL_NOT_EXIST -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "Email was not found.";
                loginMessage = "Login attempt failed; loginService was not able to find the entered email in the database";
            }
            case FAIL_INCORRECT_PASSWORD -> {
                httpStatus = HttpStatus.EXPECTATION_FAILED;
                message = "Incorrect password.";
                loginMessage = "Login attempt failed; the entered password does not match the user's password in the database";
            }
        }

        /* return Response */
        final String finalLoginMessage = loginMessage;
        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                new HashMap<>() {{
                    put("loginStatus", loginStatus);
                    put("loginEmail", login.getEmail());
                    put("loginMessage", finalLoginMessage);
                }}
        );
    }
}
