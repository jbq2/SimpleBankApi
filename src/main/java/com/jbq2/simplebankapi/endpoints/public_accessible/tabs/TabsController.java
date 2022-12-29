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
    private TabsService tabsService;

    @GetMapping("/tabs")
    public ResponseEntity<?> getTabs(@RequestHeader String jwt) {
        Map<String, String> tabMap = tabsService.getTabs(jwt);
        return new ResponseEntity<>(tabMap, HttpStatus.OK);
    }
}
