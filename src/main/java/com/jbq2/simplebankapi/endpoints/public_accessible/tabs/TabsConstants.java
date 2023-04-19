package com.jbq2.simplebankapi.endpoints.public_accessible.tabs;

public class TabsConstants {
    static final Tab[] NOT_LOGGED_IN = {
            new Tab("Login", "/login"),
            new Tab("Register", "/register")
    };

    static final Tab[] LOGGED_IN_ADMIN = {
            new Tab("Dashboard", "/dashboard"),
            new Tab("Accounts", "/accounts"),
            new Tab("Profile", "/profile"),
            new Tab("Admin", "#"),
            new Tab("Logout", "#")
    };

    static final Tab[] LOGGED_IN_USER = {
            new Tab("Dashboard", "/dashboard"),
            new Tab("Accounts", "/accounts"),
            new Tab("Profile", "/profile"),
            new Tab("Logout", "#")
    };
}
