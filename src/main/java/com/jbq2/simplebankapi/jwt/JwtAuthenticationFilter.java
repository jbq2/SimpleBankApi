package com.jbq2.simplebankapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component class that filters JSON web tokens in the "Authorization" of a request to a protected endpoint.
 * @author Joshua Quizon
 * @version 0.1
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtConstants jwtConstants;

    /**
     * Initializes the JwtConstants attribute of the JwtAuthenticationFilter class
     * @param jwtConstants Provides static variables for decoding JSON web tokens.
     */
    public JwtAuthenticationFilter(JwtConstants jwtConstants) {
        this.jwtConstants = jwtConstants;
    }

    /**
     * Properly filters out invalid JSON web tokens and lets valid JSON web tokens through.
     * @param request HttpServletRequest bean created upon application startup.
     * @param response HttpServletResponse bean created upon application startup.
     * @param chain FilterChain bean created upon application startup.
     * @throws ServletException Throws a ServletException if doFilter throws a ServletException.
     * @throws IOException Throws an IOException if doFilter throws an IOException.
     */
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

            List<GrantedAuthority> authorities = claim.asList(String.class)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        }
        catch(Exception e) {
            chain.doFilter(request, response);
        }
    }
}
