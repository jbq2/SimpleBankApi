package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.session_management.SessionService;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    public AuthenticationManager manager;
    public SessionService sessionService;
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
        try{
            /*
            * upon successful login, register a session for the user
            * get the user details to gather the authorities--put authorities in a list of string
            * this will be returned if all goes well
            * */
            final String sessionId = sessionService.registerSession(loginForm.getEmail());
            UserDetails userDetails = userService.loadUserByUsername(loginForm.getEmail());
            Collection<? extends GrantedAuthority> grantedAuthoritiesCollection = userDetails.getAuthorities();
            List<String> authorities = new ArrayList<>();
            for(GrantedAuthority auth : grantedAuthoritiesCollection){
                authorities.add(auth.getAuthority());
            }
            return new ResponseEntity<>(
                    new LoginResponse(sessionId,  userDetails.getUsername(), authorities, "Successfully logged in!"),
                    HttpStatus.OK
            );
        }
        catch(RuntimeException e){
            /*
            * runtime exception is thrown if there is a duplicate key violation when registering a session
            * this happens when a user has already signed in
            * */
            return new ResponseEntity<>(
                    "There is already an existing session.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @GetMapping("/verify")
    public ResponseEntity<?> isLoggedIn(@RequestHeader String sessionId) throws JsonProcessingException {
        /*
        * verifies if a user is logged in
        * always returns a 200 OK
        * if an error is thrown by touchSession (because of no session existing), then return false in response
        * otherwise, return true (because session exists for user)
        * */
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            sessionService.touchSession(sessionId);
            return new ResponseEntity<>(objectMapper.writeValueAsString(true), HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(objectMapper.writeValueAsString(false), HttpStatus.OK);
        }
    }
}
