package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Controller that handles requests for obtaining the correct tabs.
 * @author Joshua Quizon
 * @version 0.1
 */
@RequestMapping("/api/v1")
@RestController
public class TabsController {
    private final TabsService tabsService;

    /**
     * Initializes the TabsService attribute of the TabsController object.
     * @param tabsService Provides methods to obtain the correct set of tabs.
     */
    public TabsController(TabsService tabsService) {
        this.tabsService = tabsService;
    }

    /**
     * Non-protected GET endpoint that accepts requests to get the list of navigation bar tabs.
     * @param jwt The JSON web token included in the "jwt" header of the request.
     * @return Returns a ResponseEntity object that includes a list of tabs and a 200 OK HTTP status code.
     */
    @GetMapping("/tabs")
    public ResponseEntity<?> getTabs(@RequestHeader String jwt) {
        List<Tab> tabs = tabsService.getTabs(jwt);
        return new ResponseEntity<>(tabs, HttpStatus.OK);
    }
}
