package com.jbq2.simplebankapi.endpoints.public_accessible.signout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles requests to log a user out.
 * @author Joshua Quizon
 * @version 0.1
 */
@RequestMapping("/api/v1")
@RestController
public class LogoutController {

    /**
     * Non-protected GET endpoint that simply returns true if the request to log out is permitted.
     * @return Returns a ResponseEntity object that contains "true" and a 200 OK HTTP status code.
     * @throws JsonProcessingException Throws a JsonProcessingException if the writeValueAsString method throws its JsonProcessingException.
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(mapper.writeValueAsString(true), HttpStatus.OK);
    }
}
