package com.jbq2.simplebankapi.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles requests to use useful functions that do not necessarily belong to any other specific class.
 * @author Joshua Quizon
 * @version 0.1
 */
@RequestMapping("/api/v1/functions")
@RestController
public class FunctionsController {
    private final FunctionsService functions;

    /**
     * Initializes the FunctionsService attribute of the FunctionsController class.
     * @param functionsService Contains methods for providing JSON web tokens and checking user login status.
     */
    public FunctionsController(FunctionsService functionsService) {
        this.functions = functionsService;
    }

    /**
     * POST non-protected endpoint that returns a response object depicting the login status of a user.
     * @param jwt The JSON web token included in the "jwt" request header.
     * @return Returns a ResponseEntity containing an IsLoggedInResponse body and an HTTP status code of either 200 OK or 401 UNAUTHORIZED.
     */
    @PostMapping("/isLoggedIn")
    public ResponseEntity<?> isLoggedIn(@RequestHeader String jwt) {
        if(functions.isLoggedIn(jwt)) {
            return new ResponseEntity<>(new IsLoggedInResponse(true, functions.updateUserJwtExpiry(jwt)), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new IsLoggedInResponse(false, null), HttpStatus.UNAUTHORIZED);
        }
    }
}
