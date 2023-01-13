package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Controller class that handles requests to update user profiles.
 * @author Joshua Quizon
 * @version 0.1
 */
@RequestMapping("/api/v1/user")
@RestController
public class UpdateProfileController {
    private final UpdateProfileService updateProfileService;
    private final ObjectMapper mapper;
    private final FunctionsService functions;
    private final JwtConstants jwtConstants;

    /**
     * Initializes the 4 attributes of the UpdateProfileController class.
     * @param updateProfileService Provides business logic that allows for updating user details in the database.
     * @param objectMapper Allows for serializing objects into JSON format.
     * @param functionsService Provides a method for creating JSON web tokens.
     * @param jwtConstants Provides static variables for decoding JSON web tokens.
     */
    public UpdateProfileController(UpdateProfileService updateProfileService, ObjectMapper objectMapper, FunctionsService functionsService, JwtConstants jwtConstants) {
        this.updateProfileService = updateProfileService;
        this.mapper = objectMapper;
        this.functions = functionsService;
        this.jwtConstants = jwtConstants;
    }

    /**
     * Login-protected GET endpoint that provides the web application with the page content personalized to the logged-in user.
     * @param jwt The JSON web token of the user obtained from the "jwt" header of the request.
     * @return Returns a ResponseEntity containing the email address of the user.
     * @throws JsonProcessingException Throws a JsonProcessingException if the writeValueAsString() method throws a JsonProcessingException.
     */
    @GetMapping("/content")
    public ResponseEntity<?> getPageContent(@RequestHeader String jwt) throws JsonProcessingException {
        String subject = updateProfileService.getPageContent(jwt);
        return ResponseEntity.ok().body(mapper.writeValueAsString(subject));
    }

    /**
     * Login-protected POST endpoint that accepts the update profile form contents.
     * @param updateProfileForm Contains the information the user entered as well as the user's old information.
     * @return Returns a ResponseEntity object containing an UpdateProfileResponse object and a status code which can
     * differ between a 200 OK, 401 UNAUTHORIZED, or 417 EXPECTATION FAILED
     */
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
