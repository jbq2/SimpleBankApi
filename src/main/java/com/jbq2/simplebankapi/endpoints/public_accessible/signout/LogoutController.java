package com.jbq2.simplebankapi.endpoints.public_accessible.signout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.token_management.ExpiredTokenService;
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
public class LogoutController {
    private ExpiredTokenService expiredTokenService;

    @GetMapping("/signout")
    public ResponseEntity<?> signOut(@RequestHeader String jwt) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if(expiredTokenService.add(jwt) == null) {
            return new ResponseEntity<>(mapper.writeValueAsString(true), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(mapper.writeValueAsString(false), HttpStatus.OK);
        }
    }
}
