package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UpdateProfileController {
    private UpdateProfileService updateProfileService;
    private ObjectMapper mapper;
    private FunctionsService functions;

    @GetMapping("/content")
    public ResponseEntity<?> getPageContent(@RequestHeader String jwt) throws JsonProcessingException {
        String subject = updateProfileService.getPageContent(jwt);
        return ResponseEntity.ok().body(mapper.writeValueAsString(subject));
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileForm updateProfileForm) {
        try {
            updateProfileService.updateProfile(updateProfileForm);
            DecodedJWT decodedJWT = JWT.decode(updateProfileForm.getJwt());
            String newJwt = functions.createUserJwt(updateProfileForm.getEmail(), decodedJWT.getClaim("authorities").asArray(String.class), new Date(System.currentTimeMillis() + 600_000));
            return new ResponseEntity<>(new UpdateProfileResponse(updateProfileForm.getEmail(), "Successfully updated profile.", newJwt), HttpStatus.OK);
        }
        catch(RuntimeException e) {
            String newJwt = functions.updateUserJwtExpiry(updateProfileForm.getJwt());
            return new ResponseEntity<>(new UpdateProfileResponse(updateProfileForm.getOldEmail(), e.getMessage(), newJwt), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }
}
