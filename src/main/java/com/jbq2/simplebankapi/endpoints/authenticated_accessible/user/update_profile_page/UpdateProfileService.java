package com.jbq2.simplebankapi.endpoints.authenticated_accessible.user.update_profile_page;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import com.jbq2.simplebankapi.user_packages.user.User;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class that performs business logic to validate and process the request to update a user's profile.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class UpdateProfileService {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConstants jwtConstants;

    /**
     * Initializes the 3 attributes of the UpdateProfileService class through constructor injection.
     * @param userService Provides services for updating user details.
     * @param bCryptPasswordEncoder Encrypts strings using the BCrypt algorithm.
     * @param jwtConstants Provides static variables for decoding JSON web tokens.
     */
    public UpdateProfileService(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtConstants jwtConstants) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtConstants = jwtConstants;
    }

    /**
     * Checks for the JSON web token's validity to return page content.
     * @param jwt The user's JSON web token.
     * @return If the JSON web token is valid, the email of the web token is returned.
     * @throws RuntimeException Throws a RuntimeException if the verify() method throws its Exception.
     */
    public String getPageContent(String jwt) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
            return decodedJWT.getSubject();
        }
        catch(Exception e) {
            throw new RuntimeException("invalid jwt");
        }
    }

    /**
     * Validates the update profile form inputs and then applies the update use the UserService object.
     * @param updateProfileForm Contains the information the user entered as well as the user's old information.
     * @return Returns the user's updated email address.
     * @throws RuntimeException Throws a RuntimeException if the form fields are invalid or if the new email already exists in the database.
     */
    public String updateProfile(UpdateProfileForm updateProfileForm) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        Matcher matcher = pattern.matcher(updateProfileForm.getPassword());
        if(!matcher.find()){
            throw new RuntimeException("Invalid password.");
        }
        if(!updateProfileForm.getPassword().equals(updateProfileForm.getMatching())){
            throw new RuntimeException("Non-matching passwords.");
        }

        UserDetails userDetails = userService.loadUserByUsername(updateProfileForm.getOldEmail());
        if(!bCryptPasswordEncoder.matches(updateProfileForm.getOldPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        User user = new User();
        user.setEmail(updateProfileForm.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(updateProfileForm.getPassword()));
        try {
            userService.updateExistingUser(user, updateProfileForm.getOldEmail());
            return updateProfileForm.getEmail();
        }
        catch(RuntimeException e) {
            throw new RuntimeException("Email already exists.");
        }
    }
}
