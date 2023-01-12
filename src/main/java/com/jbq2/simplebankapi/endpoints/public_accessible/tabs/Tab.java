package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import lombok.Data;

/**
 * This class is a normal Java object that contains the information for each navigation bar tab.
 * @author Joshua Quizon
 * @version 0.1
 */
@Data
public class Tab {
    private String name;
    private String path;

    /**
     * This constructor initializes the attributes of the Tab object.
     * @param name A String that depicts the name of the tab.
     * @param path A String that depicts the URL or path that the tab leads to.
     */
    public Tab(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
