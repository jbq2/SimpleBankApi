package com.jbq2.simplebankapi.endpoints.public_accessible.signout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.session_management.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class SignOutController {
    private SessionService sessionService;

    @GetMapping("/signout")
    public ResponseEntity<?> signOut(@RequestHeader String sessionId) throws JsonProcessingException {
        sessionService.deleteSession(sessionId);
        ObjectMapper objectMapper = new ObjectMapper();
        return new ResponseEntity<>(objectMapper.writeValueAsString("session ID " + sessionId + " invalidated"), HttpStatus.OK);
    }
}
