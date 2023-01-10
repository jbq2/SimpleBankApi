package com.jbq2.simplebankapi.endpoints.public_accessible.login;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.token_management.ExpiredTokenService;
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


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {
    public AuthenticationManager manager;
    public ExpiredTokenService expiredTokenService;
    private UserService userService;
    private FunctionsService functions;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
        try{
            /*
            * upon successful login, register a session for the user
            * get the user details to gather the authorities--put authorities in a list of string
            * this will be returned if all goes well
            * */
            UserDetails userDetails = userService.loadUserByUsername(loginForm.getEmail());
            Collection<? extends GrantedAuthority> grantedAuthoritiesCollection = userDetails.getAuthorities();
            List<String> authorities = new ArrayList<>();
            for(GrantedAuthority auth : grantedAuthoritiesCollection){
                authorities.add(auth.getAuthority());
            }
//            Algorithm algorithm = Algorithm.HMAC256("secret");
//            String jwt = JWT.create()
//                    .withSubject(userDetails.getUsername())
//                    .withArrayClaim("authorities", authorities.toArray(new String[0]))
//                    .withExpiresAt(new Date(System.currentTimeMillis() + 600_000))
//                    .sign(algorithm);
            String jwt = functions.createUserJwt(userDetails.getUsername(), authorities.toArray(new String[0]), new Date(System.currentTimeMillis() + 600_000));
            if(expiredTokenService.exists(jwt)) {
                expiredTokenService.pop(jwt);
            }
            return new ResponseEntity<>(
                    new LoginResponse(jwt, "Successfully logged in!"),
                    HttpStatus.OK
            );
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(
                    "There is already an existing session.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
