package com.jbq2.simplebankapi.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class FunctionsService {
    private JwtConstants jwtConstants;

    public String updateUserJwtExpiry(String jwt) {
        DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
        String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);
        return JWT.create()
                .withSubject(decodedJwt.getSubject())
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + 600_000))
                .sign(Algorithm.HMAC256(jwtConstants.key));
    }

    public String createUserJwt(String subject, String[] authorities, Date expiryTime) {
        return JWT.create()
                .withSubject(subject)
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(expiryTime)
                .sign(Algorithm.HMAC256(jwtConstants.key));
    }

    public boolean isLoggedIn(String jwt) {
        try{
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
            if(!decodedJwt.getExpiresAt().before(new Date())) {
                return true;
            }
            else{
                return false;
            }
        }
        catch(RuntimeException e) {
            return false;
        }
    }
}
