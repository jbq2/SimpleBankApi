package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tab {
    private String name;
    private String path;
}
