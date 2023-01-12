package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class LoginController {
    public AuthenticationManager manager;
    private UserService userService;
    private FunctionsService functions;

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
