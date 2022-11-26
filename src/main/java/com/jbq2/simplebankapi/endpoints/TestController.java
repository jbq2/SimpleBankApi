package com.jbq2.simplebankapi.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/list")
    public List<String> list(){
        return List.of("you", "are", "authenticated");
    }
}
