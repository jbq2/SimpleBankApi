package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.session_management.SessionService;
import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TabsController {
    /*
    * TabsController is for returning tabs depending on user authorities
    * if the user is not logged in, Login and Register tabs are returned
    * if user IS logged in, tabs are set accordingly based on their authorities (ADMIN or USER)
    * */
    private UserService userService;

    @GetMapping("/tabs")
    public ResponseEntity<?> getTabs(@RequestHeader String jwt){
        if (jwt == null) {
            /*
            * email is null if there exists no email tied to the passed jwt
            * if this is the case, then default the tabs to Register and Login and return
            * */
            return new ResponseEntity<>(new HashMap<>(){{
                put("Register", "#");
                put("Login", "#");
            }}, HttpStatus.UNAUTHORIZED);
        }
        DecodedJWT decodedJWT = JWT.decode(jwt);
        String email = decodedJWT.getSubject();;
        /*
        * get the authorities of the user
        * if ADMIN, tabs = {Accounts, Profile, Admin, Sign Out}
        * if USER, tabs = {Accounts, Profile, Sign Out}
        * */
        Collection<? extends GrantedAuthority> grantedAuthoritiesCollection = userService.loadUserByUsername(email).getAuthorities();
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority auth : grantedAuthoritiesCollection){
            authorities.add(auth.getAuthority());
        }
        HashMap<String, String> tabMap = new HashMap<>();
        if(authorities.contains("ADMIN")) {
            tabMap.put("Dashboard", "#");
            tabMap.put("Accounts", "#");
            tabMap.put("Profile", "#");
            tabMap.put("Admin", "#");
            tabMap.put("Sign Out", "#");
        }
        else if(authorities.contains("USER")) {
            tabMap.put("Dashboard", "#");
            tabMap.put("Accounts", "#");
            tabMap.put("Profile", "#");
            tabMap.put("Sign Out", "#");
        }
        return new ResponseEntity<>(tabMap, HttpStatus.OK);
    }
}
