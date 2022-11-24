package com.jbq2.simplebankapi.session_management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class UserSessionService {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    /* createUserSession will only be called in the backend and not from a controller */
    public String createUserSession(String email, HttpServletRequest request) throws JsonProcessingException {
        UserDetails userDetails = userService.loadUserByUsername(email);

        List<String> sessionInfo = (List<String>) request.getSession().getAttribute(email);
        if(sessionInfo == null){
            sessionInfo = new ArrayList<>();
            request.getSession().setAttribute(email, sessionInfo);
        }

        String userDetailsJson = objectMapper.writeValueAsString(userDetails);
        sessionInfo.add(userDetailsJson);
        request.getSession().setAttribute("SESSION_INFO", sessionInfo);
        return userDetailsJson;
    }

    /* TODO: method to read from session */
    public UserDetails getUserSessionData(String email, HttpServletRequest request) throws JsonProcessingException {
        String userDetailsJson = (String) request.getSession().getAttribute("email");
        return objectMapper.readValue(userDetailsJson, UserDetails.class);
    }

    public void deleteUserSession(HttpServletRequest request){
        request.getSession().invalidate();
    }
}
