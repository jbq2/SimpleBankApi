package com.jbq2.simplebankapi.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jbq2.simplebankapi.constant.SecurityConstant;
import com.jbq2.simplebankapi.userpackage.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
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
    public List<GrantedAuthority> getAuthorities(String token){
        String[] authoritiesArray = getClaimFromToken(token);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String auth : authoritiesArray){
            authorities.add(new SimpleGrantedAuthority(auth));
        }
        return authorities;
    }

    /* TODO: String[] getClaimsFromToken(String token) */
    private String[] getClaimFromToken(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
    }

    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request){
        /* no need to pass credentials because token is already assumed to be verified */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    public boolean isTokenValid(String email, String token){
        JWTVerifier verifier = getJwtVerifier();
        return email != null && isTokenExpired(verifier, token);
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public String getSubject(String token){
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }

    /* TODO: JWTVerifier getJwtVerifier(String token) */
    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.BLUE_PIG_BANK).build();
        }
        catch(JWTVerificationException e){
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
    private String[] getClaimFromUser(User user) {
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority authority : user.getAuthorities()){
            authorities.add(authority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }
}
