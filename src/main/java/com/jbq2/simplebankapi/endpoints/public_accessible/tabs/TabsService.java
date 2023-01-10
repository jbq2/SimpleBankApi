package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.token_management.ExpiredTokenService;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TabsService {
    private UserService userService;
    private FunctionsService functions;
    private ExpiredTokenService expiredTokenService;

    public List<Tab> getTabs(String jwt) {
        List<Tab> tabs = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        if (!functions.isLoggedIn(jwt)) {
            /*
             * email is null if there exists no email tied to the passed jwt
             * if this is the case, then default the tabs to Register and Login and return
             * */
            tabs.add(new Tab("Login", "/login"));
            tabs.add(new Tab("Register", "/register"));
        }
        else{
            String oldJwt = jwt;
            jwt = functions.updateUserJwtExpiry(jwt);
            DecodedJWT decodedJWT = JWT.decode(jwt);
            String email = decodedJWT.getSubject();
            Collection<? extends GrantedAuthority> authorities = userService.loadUserByUsername(email).getAuthorities();

            boolean isAdmin = false;
            boolean isUser = false;
            for(GrantedAuthority auth : authorities) {
                if(auth.getAuthority().equals("ADMIN")) {
                    isAdmin = true;
                }
                if(auth.getAuthority().equals("USER")) {
                    isUser = true;
                }
            }

            if(isAdmin) {
                tabs.add(new Tab("Dashboard", "/dashboard"));
                tabs.add(new Tab("Accounts", "/accounts"));
                tabs.add(new Tab("Profile", "/profile"));
                tabs.add(new Tab("Admin", "#"));
                tabs.add(new Tab("Logout", "#"));
            }
            if(isUser && !isAdmin) {
                tabs.add(new Tab("Dashboard", "/dashboard"));
                tabs.add(new Tab("Accounts", "/accounts"));
                tabs.add(new Tab("Profile", "/profile"));
                tabs.add(new Tab("Logout", "#"));
            }

            if(expiredTokenService.add(oldJwt) == null) {
                throw new RuntimeException();
            }
        }
        return tabs;
    }
}
