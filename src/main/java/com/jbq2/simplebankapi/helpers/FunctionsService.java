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

    public boolean isActiveJwt(String jwt) {
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

    public String updateJwt(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DecodedJWT decodedJwt = JWT.decode(jwt);
        return JWT.create()
                .withSubject(decodedJwt.getSubject())
                .withArrayClaim("authorities", decodedJwt.getClaims().keySet().toArray(new String[0]))
                .withExpiresAt(new Date(System.currentTimeMillis() + 300_000))
                .sign(algorithm);
    }
}
