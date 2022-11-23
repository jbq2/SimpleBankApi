package com.jbq2.simplebankapi.user_packages;

import com.jbq2.simplebankapi.user_packages.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TestController {
    UserService userService;

    @GetMapping("/test")
    public UserDetails getAuthorities(){
        return userService.loadUserByUsername("jbq2@njit.edu");
    }
}
