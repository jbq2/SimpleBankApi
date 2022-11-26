package com.jbq2.simplebankapi.config;

import com.jbq2.simplebankapi.session_management.SessionFilter;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/*
* NOTE: this is for testing purposes ONLY
* A proper Session registry will be created in the DB
* */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AppConfig {
    private final  UserService userService;
    private final SessionFilter sessionFilter;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()

                /* for dev purposes, requests to /api/v1/test will not be checked for authorization */
                .authorizeRequests().antMatchers("/api/v1/register", "/api/v1/login").permitAll()

                .anyRequest().authenticated().and()

                .addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling().authenticationEntryPoint(
                        ((request, response, exception) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                        })
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
