package com.jbq2.simplebankapi.endpoints.public_accessible.registration;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody RegistrationForm registrationForm){

        try{
            final String registrationEmail = registrationService.validateAndSave(registrationForm);
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
