package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * This class is a controller class that is responsible for user login.
 * @author Joshua Quizon
 * @version 0.1
 */
@RequestMapping("/api/v1")
@RestController
public class LoginController {
    public AuthenticationManager manager;
    private UserService userService;
    private FunctionsService functions;

    /**
     * This constructor initializes all 3 attributes of LoginController through constructor injection.
     * @param authenticationManager An object of type AuthenticationManager that was created in the authenticationManager method in AppConfig.
     * @param userService An object of type UserService that implements UserDetails service and provides methods for gathering user data.
     * @param functionsService An object of type FunctionsService that is responsible for providing various helpful methods.
     */
    public LoginController(AuthenticationManager authenticationManager, UserService userService, FunctionsService functionsService) {
        this.manager = authenticationManager;
        this.userService = userService;
        this.functions = functionsService;
    }

    /**
     * This method is a POST request endpoint that authenticates and handles login requests.
     * @param loginForm An object of type LoginForm that matches the attributes of the login form from the front end.
     * @return Returns a ResponseEntity that holds a LoginResponse object and the HttpStatus.
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        try{
            UserDetails userDetails = userService.loadUserByUsername(loginForm.getEmail());
            Collection<? extends GrantedAuthority> grantedAuthoritiesCollection = userDetails.getAuthorities();
            List<String> authorities = new ArrayList<>();
            for(GrantedAuthority auth : grantedAuthoritiesCollection){
                authorities.add(auth.getAuthority());
            }

            String jwt = functions.createUserJwt(userDetails.getUsername(), authorities.toArray(new String[0]), new Date(System.currentTimeMillis() + 600_000));

            return new ResponseEntity<>(
                    new LoginResponse(jwt, "Successfully logged in!"),
                    HttpStatus.OK
            );
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(
                    "Account does not exist.",
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
