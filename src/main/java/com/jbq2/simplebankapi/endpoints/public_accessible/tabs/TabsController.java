package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class TabsController {

    private TabsService tabsService;

    @GetMapping("/tabs")
    public ResponseEntity<?> getTabs(@RequestHeader String jwt) {
        List<Tab> tabs = tabsService.getTabs(jwt);
        return new ResponseEntity<>(tabs, HttpStatus.OK);
    }
}
