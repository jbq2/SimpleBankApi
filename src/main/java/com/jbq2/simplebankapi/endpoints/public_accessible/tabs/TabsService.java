package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jbq2.simplebankapi.helpers.FunctionsService;
import com.jbq2.simplebankapi.jwt.JwtConstants;
import com.jbq2.simplebankapi.user_packages.role.RoleEnum;
import com.jbq2.simplebankapi.user_packages.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that contains the logic that finds the correct set of navigation bar tabs depending on login status and user authorities.
 * @author Joshua Quizon
 * @version 0.1
 */
@Service
public class TabsService {
    private final UserService userService;
    private final FunctionsService functions;
    private final JwtConstants jwtConstants;

    /**
     * Initializes all 3 attributes of the TabsService object.
     * @param userService Provides services for gathering and saving user details from and to the database.
     * @param functionsService Provides useful services for checking login status and creating and updating JSON web tokens.
     * @param jwtConstants Includes static variables used for decoding JSON web tokens.
     */
    public TabsService(UserService userService, FunctionsService functionsService, JwtConstants jwtConstants) {
        this.userService = userService;
        this.functions = functionsService;
        this.jwtConstants = jwtConstants;
    }

    /**
     * Creates a list of tabs depending on the login status of the user and the authorities of the user.
     * @param jwt The JSON web token of the user.
     * @return Returns a List of Tab objects.
     */
    public List<Tab> getTabs(String jwt) {
        List<Tab> tabs;
        if (!functions.isLoggedIn(jwt)) {
            tabs = Arrays.asList(TabsConstants.NOT_LOGGED_IN);
        }
        else{
            String newJwt = functions.updateUserJwtExpiry(jwt);
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtConstants.key)).build().verify(newJwt);
            String email = decodedJWT.getSubject();
            Collection<? extends GrantedAuthority> authorities = userService.loadUserByUsername(email).getAuthorities();

            Set<RoleEnum> roleSet = authorities
                    .stream()
                    .map(auth -> auth.getAuthority().equals("ADMIN") ? RoleEnum.ADMIN : RoleEnum.USER)
                    .collect(Collectors.toSet());

            tabs = (roleSet.contains(RoleEnum.ADMIN)) ? Arrays.asList(TabsConstants.LOGGED_IN_ADMIN) : Arrays.asList(TabsConstants.LOGGED_IN_USER);
        }

        return tabs;
    }
}
