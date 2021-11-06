package com.java.training.application.model;

public enum UserRole {
    USER,
    ADMINISTRATOR;

    public boolean isRole(final String userRole) {
        boolean result = false;
        for (final UserRole role : UserRole.values()) {
            if (role.name().equals(userRole)) {
                result = true;
            }
        }
        return result;
    }
}
