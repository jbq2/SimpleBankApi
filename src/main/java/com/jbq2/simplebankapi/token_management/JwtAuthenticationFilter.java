package com.jbq2.simplebankapi.token_management;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;

@Component
@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(7);
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(jwt);
            Claim claim = decodedJWT.getClaim("authorities");
            List<String> authoritiesList = claim.asList(String.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for(String authority : authoritiesList) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
            System.out.println(authorities.toString());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        }
        catch(Exception e) {
            chain.doFilter(request, response);
        }
    }
}
