package com.jbq2.simplebankapi.session_management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/session")
@AllArgsConstructor
public class UserSessionController {
    private UserSessionService userSessionService;
    private HttpServletRequest httpServletRequest;

    /* TODO: this is only for testing, so remove afterwards */
    @PostMapping("/createTest")
    public CustomResponse createSession(@RequestParam("email") String email) throws JsonProcessingException {
        /* initialize CustomResponse variables */
        ResponseType responseType = ResponseType.SUCCESS;
        HttpStatus httpStatus = HttpStatus.OK;
        String message = "SUCCESS";
        String userDetailsJson = null;

        /* try catch to handle errors from createUserSession */
        try{
            userDetailsJson = userSessionService.createUserSession(email, httpServletRequest);
        }
        catch(Exception e){
            responseType = ResponseType.ERROR;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            message = e.toString();
        }

        /* create body, fill if userDetailsJson is not null */
        Map<String, String> body = null;
        if(userDetailsJson != null){
            body = new HashMap<>();
            body.put("userDetailsJson", userDetailsJson);
        }

        return new CustomResponse(
                responseType,
                httpStatus,
                httpStatus.value(),
                message,
                body
        );
    }

    @GetMapping("/authoritiesOfUser")
    public CustomResponse getUserAuthorities(@RequestParam("email") String email) throws JsonProcessingException {
        /* initialize variables */
        Collection<? extends GrantedAuthority> authorities = null;
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseType responseType = ResponseType.SUCCESS;
        String message = "SUCCESS";

        try{
            /* should return null and throw Exception if getAuthorities() fails */
            authorities = userSessionService.getUserSessionData(email, httpServletRequest).getAuthorities();
        }
        catch(Exception e){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseType = ResponseType.ERROR;
            message = "Unable to gather session data from " + email;
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

    @DeleteMapping("/deleteUserSession")
    public CustomResponse deleteUserSession(@RequestParam("email") String email){
        userSessionService.deleteUserSession(email, httpServletRequest);

        return new CustomResponse(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "DELETED SESSION " + email,
                null
        );
    }

    @DeleteMapping("/deleteEntireSession")
    public CustomResponse deleteEntireSession(){
        userSessionService.deleteEntireSession(httpServletRequest);

        return new CustomResponse(
                ResponseType.SUCCESS,
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "DELETED ENTIRE SESSION",
                null
        );
    }
}
