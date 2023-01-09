package com.jbq2.simplebankapi.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class FunctionsService {

    public String updateJwt(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DecodedJWT decodedJwt = JWT.decode(jwt);
        String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);
        return JWT.create()
                .withSubject(decodedJwt.getSubject())
                .withArrayClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + 300_000))
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
