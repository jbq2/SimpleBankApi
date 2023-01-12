package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This class is a service that contains the logic that finds the correct set of navigation bar tabs depending on login status and user authorities.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class TabsService {

    private final UserService userService;
    private final FunctionsService functions;
    private final JwtConstants jwtConstants;

    /**
     * This constructor initializes all 3 attributes of the TabsService object.
     * @param userService An object of type UserService which handles the gathering and saving of user details.
     * @param functionsService An object of type FunctionsService that has useful methods for checking login status and creating and updating JSON web tokens.
     * @param jwtConstants An object of type JwtConstants that includes static variables used for JSON web token decoding.
     */
    public TabsService(UserService userService, FunctionsService functionsService, JwtConstants jwtConstants) {
        this.userService = userService;
        this.functions = functionsService;
        this.jwtConstants = jwtConstants;
    }

    /**
     * This method creates a list of tabs depending on the login status of the user and the authorities of the user.
     * @param jwt A String depicting the JSON web token of the user.
     * @return Returns a List of Tab objects.
     */
    public List<Tab> getTabs(String jwt) {
        List<Tab> tabs = new ArrayList<>();
        if (!functions.isLoggedIn(jwt)) {
            tabs.add(new Tab("Login", "/login"));
            tabs.add(new Tab("Register", "/register"));
        }
        else{
            String newJwt = functions.updateUserJwtExpiry(jwt);
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(newJwt);
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
        }

        return tabs;
    }
}
