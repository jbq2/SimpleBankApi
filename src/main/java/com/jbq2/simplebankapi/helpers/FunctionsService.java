package com.jbq2.simplebankapi.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class FunctionsService {

    public String updateUserJwtExpiry(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DecodedJWT decodedJwt = JWT.decode(jwt);
        String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);
        return JWT.create()
                .withSubject(decodedJwt.getSubject())
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + 600_000))
                .sign(algorithm);
    }

    public String createUserJwt(String subject, String[] authorities, Date expiryTime) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withSubject(subject)
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(expiryTime)
                .sign(algorithm);
    }

    public boolean isLoggedIn(String jwt) {
        try{
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWT.require(algorithm).build().verify(jwt);
            DecodedJWT decodedJwt = JWT.decode(jwt);
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
