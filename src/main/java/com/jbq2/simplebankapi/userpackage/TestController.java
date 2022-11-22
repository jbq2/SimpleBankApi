package com.jbq2.simplebankapi.userpackage;

import com.jbq2.simplebankapi.userpackage.pojo.User;
import com.jbq2.simplebankapi.userpackage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TestController {
    UserService userService;

//    @GetMapping("/test")
//    public UserDetails getAuthorities(){
//        return userService.loadUserByUsername("jbq2@njit.edu");
//    }
}
