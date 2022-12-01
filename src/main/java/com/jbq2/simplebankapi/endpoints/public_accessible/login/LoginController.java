package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.jbq2.simplebankapi.session_management.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    public AuthenticationManager manager;
    public SessionService sessionService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        try{
            final String sessionId = sessionService.registerSession(loginForm.getEmail());
            return new ResponseEntity<>(
                    new LoginDto(sessionId, "Successfully logged in!"),
                    HttpStatus.OK
            );
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(
                    "There is already an existing session.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
