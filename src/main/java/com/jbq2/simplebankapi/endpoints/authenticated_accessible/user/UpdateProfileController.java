package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UpdateProfileController {
    private UpdateProfileService updateProfileService;

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileForm updateProfileForm){
        try{
            UpdateProfileResponse updateProfileResponse = updateProfileService.updateProfile(updateProfileForm);
            return new ResponseEntity<>(updateProfileResponse, HttpStatus.OK);
        }
        catch(RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
