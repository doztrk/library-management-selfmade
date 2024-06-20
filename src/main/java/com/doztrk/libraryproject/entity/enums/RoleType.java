package com.doztrk.libraryproject.entity.enums;

public enum RoleType {


    ADMIN("Admin"),
    MEMBER("Member"),
    EMPLOYEE("Employee"),
    ANONYMOUS("Anonymous");

    public final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
