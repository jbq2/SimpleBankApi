package com.jbq2.simplebankapi.config;

import com.jbq2.simplebankapi.jwt.JwtAuthenticationFilter;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * This is a configuration class for the API, setting up user authentication, protected and unprotected endpoints, and CORS.
 *
 * @author Joshua Quizon
 * @version 0.1
 */

@Configuration
@EnableWebSecurity
public class AppConfig {
    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * A constructor for AppConfig that initializes all class attributes--constructor injection is implicitly performed by Spring Framework.
     * @param userService A constructor-injected object of type UserService, responsible for providing services to gather user details.
     * @param jwtAuthenticationFilter A constructor-injected object of type JwtAuthenticationFilter, responsible for filtering JSON web tokens.
     * @param passwordEncoder A constructor-injected object of type BCryptPasswordEncoder, responsible for encoding strings with the BCrypt algorithm.
     */
    public AppConfig(UserService userService, JwtAuthenticationFilter jwtAuthenticationFilter, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method configures the AuthenticationManagerBuilder, which is then used to create the AuthenticationManager.
     * @param auth An autowired AuthenticationManagerBuilder that will have its userDetailsService and passwordEncoder configured
     *             with the constructor injected UserService and BCryptPasswordEncoder.
     * @throws Exception This method throws an Exception if the userDetailsService method throws its Exception.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    /**
     * This method configures a SecurityFilterChain to authorize requests, protect endpoints,
     * authenticate JSON web tokens, handle unauthorized requests, and apply the custom CORS configuration.
     * @param http A HttpSecurity bean produced upon application startup; meant to be configured.
     * @return Returns the built HTTP configuration which is a SecurityFilterChain object.
     * @throws Exception This method throws an Exception if any of the http methods throw their Exceptions.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/user/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(
                        ((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage()))
                ).and().cors(Customizer.withDefaults());
        return http.build();
    }

    /**
     * This method configures CORS for the application.  As of right now, all origins, headers, and methods are allowed,
     * and CORS is applied to all endpoints.
     * @return Returns an object of type CorsConfigurationSource, which is then fed into the .cors() method of the HttpSecurity object.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * This method creates a Bean of type AuthenticationManager which is used for user login purposes.
     * @param authenticationConfiguration A bean of type AuthenticationConfiguration that is created upon application
     *                                    startup and configured by configureGlobal() in AppConfig.
     * @return Returns an AuthenticationManager bean which is then used for user login in LoginController.
     * @throws Exception This method throws an Exception if the getAuthenticationManager method throws its Exception.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
