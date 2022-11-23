package com.jbq2.simplebankapi.filter;

import com.jbq2.simplebankapi.constant.SecurityConstant;
import com.jbq2.simplebankapi.utility.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)){
            response.setStatus(HttpStatus.OK.value());
        }
        else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)){
                filterChain.doFilter(request, response);
            }
        }
    }
}
