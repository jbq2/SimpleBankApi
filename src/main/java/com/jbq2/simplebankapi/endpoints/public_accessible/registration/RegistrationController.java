package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that is responsible for accepting user registration requests.
 * @author Joshua Quizon
 * @version 0.1
 */
@RequestMapping("/api/v1")
@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    /**
     * Initializes the only attribute of RegistrationController through constructor injection.
     * @param registrationService An object of type RegistrationService that is responsible for processing the registration request information.
     */
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Non-protected POST request endpoint that calls the RegistrationService object to validate and save the user information stored in the RegistrationForm object.
     * @param registrationForm Includes all the necessary user information for registration.
     * @return Returns a ResponseEntity object that holds the user's email address or an error message depending on how validation and saving went.
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody RegistrationForm registrationForm){

        try{
            String registrationEmail = registrationService.validateAndSave(registrationForm);
            return new ResponseEntity<>(
                    new RegistrationResponse(registrationEmail, "Successfully registered!"),
                    HttpStatus.OK
            );
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
