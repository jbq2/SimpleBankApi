package com.jbq2.simplebankapi.user_packages.role;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum that implements GrantedAuthority and holds the constants for the ADMIN and USER authorities.
 * @author Joshua Quizon
 * @version 0.1
 */
public enum RoleEnum implements GrantedAuthority {
    ADMIN((long) 1), USER((long) 2);
    private final Long value;

    RoleEnum(Long value) {
        this.value = value;
    }

    /**
     * Gets the value of the RoleEnum object.
     * @return Returns a Long that is the value.
     */
    public Long getValue(){
        return value;
    }

    /**
     * Gets the name of the authority using the name() method of enums.
     * @return Returns a String that is the name of the authority.
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
