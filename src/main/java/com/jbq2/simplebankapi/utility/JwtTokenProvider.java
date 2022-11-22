package com.jbq2.simplebankapi.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jbq2.simplebankapi.constant.SecurityConstant;
import com.jbq2.simplebankapi.userpackage.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(User user){
        String[] claims = getClaimFromUser(user);
        return JWT.create()
                .withIssuer(SecurityConstant.BLUE_PIG_BANK)
                .withAudience(SecurityConstant.BLUE_PIG_BANK_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(user.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    /* TODO: List<GrantedAuthority> getAuthorities(String token) */
    /* TODO: String[] getClaimsFromToken(String token) */
    /* TODO: List<GrantedAuthority> getAuthorities(String token) */
    /* TODO: JWTVerifier getJwtVerifier(String token) */

    private String[] getClaimFromUser(User user) {
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority authority : user.getAuthorities()){
            authorities.add(authority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }
}
