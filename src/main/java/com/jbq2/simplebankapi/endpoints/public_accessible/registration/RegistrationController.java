package com.jbq2.simplebankapi.endpoints.public_accessible.registration;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody RegistrationForm registrationForm){

        final String registrationEmail = registrationService.validateAndSave(registrationForm);
        return new ResponseEntity<>(
                new HashMap<String, String>() {{
                    put("EMAIL", registrationEmail);
                }},
                HttpStatus.OK
        );
    }
}
