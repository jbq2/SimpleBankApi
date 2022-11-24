package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.jbq2.simplebankapi.endpoints.public_accessible.login.exceptions.CustomLoginException;
import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import com.jbq2.simplebankapi.session_management.UserSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    private LoginService loginService;
    private UserSessionService userSessionService;

    @PostMapping("/login")
    @ResponseBody
    public CustomResponse loginUser(@RequestBody Login login) {

        /* initializing response params */
        ResponseType responseType = ResponseType.SUCCESS;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "";
        UserDetails userDetails = null;
        CustomLoginException exception = null;
        String sessionId = null;

        try{
            /* validating user login form; if not error is thrown, the login was successful */
            userDetails = loginService.validateLogin(login);
            httpStatus = HttpStatus.OK;
            message = "SUCCESS";
            sessionId = userSessionService.createUserSession(userDetails);
        }
        catch(CustomLoginException e) {
            /* exception from validateLogin() will be caught and handled as follows */
            exception = e;
            responseType = ResponseType.ERROR;
            message = e.getMessage();
        }

        /* return Response, requires finalizing certain variables to insert into CustomResponse body */
        final CustomLoginException finalException = exception;
        final String userEmail = (userDetails != null) ? userDetails.getUsername() : null;
        final Collection<? extends GrantedAuthority> authorities = (userDetails != null) ? userDetails.getAuthorities() : null;
        final String finalSessionId = sessionId;
        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                new HashMap<>() {{
                    put("EXCEPTION", finalException);
                    put("USER_EMAIL", userEmail);
                    put("AUTHORITIES", authorities);
                    put("SESSION_ID", finalSessionId);
                }}
        );
    }
}
