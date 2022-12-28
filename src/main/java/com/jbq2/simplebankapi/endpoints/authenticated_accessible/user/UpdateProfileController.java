package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.runtime.ObjectMethods;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UpdateProfileController {
    private UpdateProfileService updateProfileService;

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileForm updateProfileForm) throws JsonProcessingException {
        try{
            UpdateProfileResponse updateProfileResponse = updateProfileService.updateProfile(updateProfileForm);
            return new ResponseEntity<>(updateProfileResponse, HttpStatus.OK);
        }
        catch(RuntimeException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            if(e.getMessage().equals("DB_ERR")) {
                return new ResponseEntity<>(objectMapper.writeValueAsString("Unable to save updates to database"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else{   
                return new ResponseEntity<>(objectMapper.writeValueAsString(e.getMessage()), HttpStatus.EXPECTATION_FAILED);
            }
        }
    }
}
