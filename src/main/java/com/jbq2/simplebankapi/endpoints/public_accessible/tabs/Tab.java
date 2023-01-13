package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import lombok.Data;

/**
 * Plain-old-Java-object that contains the information for each navigation bar tab.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class Tab {
    private String name;
    private String path;

    /**
     * Initializes the attributes of the Tab object.
     * @param name The name of the tab.
     * @param path The URL or path that the tab leads to.
     */
    public Tab(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
