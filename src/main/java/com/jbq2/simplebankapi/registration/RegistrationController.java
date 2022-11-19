package com.jbq2.simplebankapi.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody Registration registration){
        return registrationService.validateAndSave(registration).name();
    }
}
