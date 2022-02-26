package com.cloudeasy.enums.role;

/**
 * Enum define user role
 */
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private String role;
    Role(String role) { this.role = role; }

    public String getRole() {
        return role;
    }
}
