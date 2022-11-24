package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jbq2.simplebankapi.endpoints.public_accessible.login.exceptions.CustomLoginException;
import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    private LoginService loginService;
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public CustomResponse loginUser(@RequestBody Login login) throws JsonProcessingException {

        /* initializing response params */
        ResponseType responseType = ResponseType.SUCCESS;
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        String message = "";
        UserDetails userDetails = null;
        CustomLoginException exception = null;

        try{
            loginService.validateLogin(login);
            message = "Login attempt successful";
            userDetails = userService.loadUserByUsername(login.getEmail());
        }
        catch(CustomLoginException e) {
            exception = e;
            responseType = ResponseType.ERROR;
            message = e.getMessage();
        }

        /* return Response */
        final CustomLoginException finalException = exception;
        final UserDetails finalUserDetails = userDetails;
        final String loginEmail = (login != null) ? login.getEmail() : null;
        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                new HashMap<>() {{
                    put("exception", finalException);
                    put("loginEmail", loginEmail);
                    put("userDetails", finalUserDetails);
                }}
        );
    }
}
