package com.jbq2.simplebankapi.session_management.PENDING_DEPRECATION;

import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import com.jbq2.simplebankapi.session_management.PENDING_DEPRECATION.exceptions.UserSessionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/session")
@AllArgsConstructor
public class UserSessionController {
    private UserSessionService userSessionService;

    /* TODO: this is only for testing, so remove afterwards */
    /*
    @PostMapping("/createTest")
    public CustomResponse createSession(@RequestParam("email") String email) throws JsonProcessingException {
        ResponseType responseType = ResponseType.SUCCESS;
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "SUCCESS";
        String userDetailsJson = null;

        try{
            userDetailsJson = userSessionService.createUserSession(email);
        }
        catch(Exception e){
            responseType = ResponseType.ERROR;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = e.toString();
        }

        Map<String, String> body = null;
        if(userDetailsJson != null){
            body = new HashMap<>();
            body.put("sessionId", userDetailsJson);
        }

        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                body
        );
    }
    */

    @GetMapping("/authoritiesOfUser")
    public CustomResponse getUserAuthorities() throws UserSessionNotFoundException {
        /* initialize variables */
        Collection<? extends GrantedAuthority> authorities = null;
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseType responseType = ResponseType.SUCCESS;
        String message = "SUCCESS";

        try{
            /* should return null and throw Exception if getAuthorities() fails */
            authorities = userSessionService.getUserSessionData();
        }
        catch(UserSessionNotFoundException e){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseType = ResponseType.ERROR;
            message = "You must be logged in to perform this action.";
        }

        /* prepare body of CustomResponse depending on responseType */
        Map<String, Collection<? extends GrantedAuthority>> body = null;
        if(responseType == ResponseType.SUCCESS){
            body = new HashMap<>();
            body.put("authorities", authorities);
        }

        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                body
        );
    }

    @DeleteMapping("/end")
    public CustomResponse endSession(){
        userSessionService.deleteSession();

        return new CustomResponse(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "ENDED SESSION",
                null
        );
    }
}
