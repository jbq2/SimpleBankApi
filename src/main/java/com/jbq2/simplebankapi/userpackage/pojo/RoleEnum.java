package com.jbq2.simplebankapi.userpackage.pojo;

public enum RoleEnum {
    ADMIN((long) 1), USER((long) 2);
    private final Long value;

    RoleEnum(Long value) {
        this.value = value;
    }

    public Long getValue(){
        return value;
    }
}
