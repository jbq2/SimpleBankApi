package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/api/v1/user")
@RestController
@AllArgsConstructor
public class UpdateProfileController {
    private UpdateProfileService updateProfileService;
    private ObjectMapper mapper;
    private FunctionsService functions;
    private JwtConstants jwtConstants;

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
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(updateProfileForm.getJwt());
            String newJwt = functions.createUserJwt(updateProfileForm.getEmail(), decodedJWT.getClaim("authorities").asArray(String.class), new Date(System.currentTimeMillis() + 600_000));
            return new ResponseEntity<>(new UpdateProfileResponse(updateProfileForm.getEmail(), "Successfully updated profile.", newJwt), HttpStatus.OK);
        }
        catch(JWTVerificationException e){
            return new ResponseEntity<>(new UpdateProfileResponse(null, "Session has expired.", null), HttpStatus.UNAUTHORIZED);
        }
        catch(RuntimeException e) {
            String newJwt = functions.updateUserJwtExpiry(updateProfileForm.getJwt());
            return new ResponseEntity<>(new UpdateProfileResponse(updateProfileForm.getOldEmail(), e.getMessage(), newJwt), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
