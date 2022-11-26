package com.jbq2.simplebankapi.config;

import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AppConfig {
    private final UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement()
                .and()
                /* for dev purposes, requests to /api/v1/test will not be checked for authorization */
                .authorizeRequests().antMatchers("/api/v1/register", "/api/v1/login", "/api/v1/test").permitAll()
                .and()
                /* TODO: /api/v1/user/** features */
                .authorizeRequests().antMatchers("/api/v1/user/**").hasAnyAuthority("USER", "ADMIN")
                .and()
                /* TODO: /api/v1/admin/** features */
                .authorizeRequests().antMatchers("api/v1/admin/**").hasAuthority("ADMIN");
        return http.build();
    }
}
