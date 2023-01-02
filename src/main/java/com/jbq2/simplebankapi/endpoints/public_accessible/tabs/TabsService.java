package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.token_management.ExpiredTokenService;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TabsService {
    private UserService userService;
    private FunctionsService functions;
    private ExpiredTokenService expiredTokenService;

    public Map<String, String> getTabs(String jwt) {
        Map<String, String> map = new HashMap<>();
        if (!functions.isLoggedIn(jwt)) {
            /*
             * email is null if there exists no email tied to the passed jwt
             * if this is the case, then default the tabs to Register and Login and return
             * */
            map.put("Register", "/register");
            map.put("Login", "/login");
        }
        else{
            String oldJwt = jwt;
            jwt = functions.updateJwt(jwt);
            DecodedJWT decodedJWT = JWT.decode(jwt);
            String email = decodedJWT.getSubject();
            Collection<? extends GrantedAuthority> grantedAuthoritiesCollection = userService.loadUserByUsername(email).getAuthorities();
            List<String> authorities = new ArrayList<>();
            for(GrantedAuthority auth : grantedAuthoritiesCollection){
                authorities.add(auth.getAuthority());
            }
            if(authorities.contains("ADMIN")) {
                map.put("Dashboard", "/user/dashboard");
                map.put("Accounts", "/user/accounts");
                map.put("Profile", "/user/profile");
                map.put("Admin", "#");
                map.put("Logout", "#");
            }
            else if(authorities.contains("USER")) {
                map.put("Dashboard", "/user/dashboard");
                map.put("Accounts", "/user/accounts");
                map.put("Profile", "/user/profile");
                map.put("Logout", "#");
            }
            if(expiredTokenService.add(oldJwt) == null) {
                throw new RuntimeException();
            }
        }
        return map;
    }
}
