package com.jbq2.simplebankapi.helpers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/functions")
@AllArgsConstructor
public class FunctionsController {

    private FunctionsService functionsService;

    @PostMapping("/isLoggedIn")
    public ResponseEntity<?> isLoggedIn(@RequestHeader String jwt) {
        if(functionsService.isLoggedIn(jwt)) {
            return new ResponseEntity<>(new IsLoggedInResponse(true, functionsService.updateUserJwtExpiry(jwt)), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new IsLoggedInResponse(false, null), HttpStatus.UNAUTHORIZED);
        }
    }
}
