package com.doztrk.libraryproject.entity.enums;

public enum RoleType {


    ADMIN("Admin"),
    MEMBER("Member"),
    EMPLOYEE("Employee");

    public final String enumRole;

    RoleType(String name) {
        this.enumRole = name;
    }

    public String getName() {
        return enumRole;
    }
}
