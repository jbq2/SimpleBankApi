package com.jbq2.simplebankapi.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service that provides methods to update and create JSON web tokens and checks for user login status.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class FunctionsService {
    private final JwtConstants jwtConstants;

    /**
     * Initializes the JwtConstants attribute of the FunctionsService class.
     * @param jwtConstants Provides static variables used for decoding JSON web tokens.
     */
    public FunctionsService(JwtConstants jwtConstants) {
        this.jwtConstants = jwtConstants;
    }

    /**
     * Updates an valid JSON web token.
     * @param jwt The user's current JSON web token that is to be updated.
     * @return Returns a new String that is the updated JSON web token.
     */
    public String updateUserJwtExpiry(String jwt) {
        DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
        String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);
        return JWT.create()
                .withSubject(decodedJwt.getSubject())
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + 600_000))
                .sign(Algorithm.HMAC256(jwtConstants.key));
    }

    /**
     * Creates a new JSON web token given the subject, an array of authorities, and an expiry time.
     * @param subject The user's email address.
     * @param authorities The authorities of the user as Strings.
     * @param expiryTime The expiry time of the newly created JSON web token.
     * @return Returns a String that is the newly created JSON web token.
     */
    public String createUserJwt(String subject, String[] authorities, Date expiryTime) {
        return JWT.create()
                .withSubject(subject)
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(expiryTime)
                .sign(Algorithm.HMAC256(jwtConstants.key));
    }

    /**
     * Checks if a user is logged in by verifying the user's JSON web token.
     * @param jwt The JSON web token of the user.
     * @return Returns true if the JSON web token is valid, and false otherwise.
     */
    public boolean isLoggedIn(String jwt) {
        try{
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
            return !decodedJwt.getExpiresAt().before(new Date());
        }
        catch(RuntimeException e) {
            return false;
        }
    }
}
