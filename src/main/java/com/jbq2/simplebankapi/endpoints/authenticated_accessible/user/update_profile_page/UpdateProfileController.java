package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UpdateProfileController {
    private UpdateProfileService updateProfileService;
    private ObjectMapper mapper;

    @GetMapping("/content")
    public ResponseEntity<?> getPageContent(@RequestHeader String jwt) throws JsonProcessingException {
        String subject = updateProfileService.getPageContent(jwt);
        return ResponseEntity.ok()
                .body(mapper.writeValueAsString(subject));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileForm updateProfileForm) throws JsonProcessingException {
        try{
            String email = updateProfileService.updateProfile(updateProfileForm);
            return new ResponseEntity<>(new UpdateProfileResponse(
                    email,
                    "Password updated"
            ), HttpStatus.OK);
        }
        catch(RuntimeException e) {
            if(e.getMessage().equals("DB_ERR")) {
                return new ResponseEntity<>(mapper.writeValueAsString("Unable to save updates to database"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else{
                return new ResponseEntity<>(mapper.writeValueAsString(e.getMessage()), HttpStatus.EXPECTATION_FAILED);
            }
        }
    }
}
