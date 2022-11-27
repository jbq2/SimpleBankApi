package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
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
    /* public InMemorySessionRegistry sessionRegistry; */

    public SessionService sessionService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, ?>> loginUser(@RequestBody LoginForm loginForm) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        /* final String sessionId = sessionRegistry.registerSession(loginForm.getEmail()); */
        final String sessionId = sessionService.registerSession(loginForm.getEmail());
        return ResponseEntity.ok(
                new HashMap<>() {{
                    put("sessionId", sessionId);
                }}
        );
    }
}
