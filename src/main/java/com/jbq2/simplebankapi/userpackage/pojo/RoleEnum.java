package com.jbq2.simplebankapi.userpackage.pojo;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ADMIN((long) 1), USER((long) 2);
    private final Long value;

    RoleEnum(Long value) {
        this.value = value;
    }

    public Long getValue(){
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
