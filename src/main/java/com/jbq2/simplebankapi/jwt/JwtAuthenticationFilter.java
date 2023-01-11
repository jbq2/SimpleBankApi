package com.jbq2.simplebankapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtConstants jwtConstants;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(7);
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(jwt);
            Claim claim = decodedJWT.getClaim("authorities");
            List<String> authoritiesList = claim.asList(String.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for(String authority : authoritiesList) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        }
        catch(Exception e) {
            chain.doFilter(request, response);
        }
    }
}
