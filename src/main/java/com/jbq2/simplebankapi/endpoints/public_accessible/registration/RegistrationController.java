package com.jbq2.simplebankapi.endpoints.public_accessible.registration;

import com.jbq2.simplebankapi.response.CustomResponse;
import com.jbq2.simplebankapi.response.ResponseType;
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
    public ResponseEntity<Map<String, ?>> register(@RequestBody RegistrationForm registrationForm){

        final String registrationEmail = registrationService.validateAndSave(registrationForm);
        return ResponseEntity.ok(
                new HashMap<>() {{
                    put("email", registrationEmail);
                }}
        );
    }
}
