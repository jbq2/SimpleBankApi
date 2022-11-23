package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import com.jbq2.simplebankapi.user_packages.dao.UserDao;
import com.jbq2.simplebankapi.user_packages.pojo.User;
import com.jbq2.simplebankapi.user_packages.service.UserRoleService;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    private LoginService loginService;
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public CustomResponse loginUser(@RequestBody Login login) throws JsonProcessingException {
        /* validating login form, returns a specific LoginStatus */
        LoginStatus loginStatus = loginService.validateLogin(login);

        /* initializing response params */
        ResponseType responseType = ResponseType.ERROR;
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        String loginMessage = "";
        UserDetails userDetails = null;

        /* customizing parameters based on returned loginStatus */
        switch(loginStatus){
            case SUCCESS -> {
                responseType = ResponseType.SUCCESS;
                httpStatus = HttpStatus.OK;
                message = "SUCCESS";
                loginMessage = "Login attempt was successful";
                userDetails = userService.loadUserByUsername(login.getEmail());
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
        ObjectMapper mapper = new ObjectMapper();
        final String userDetailsJson = (userDetails == null) ? null : mapper.writeValueAsString(userDetails);
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
                    put("userDetails", userDetailsJson);
                }}
        );
    }
}
