package org.dev.toptenplaylist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("user")
    USER,
    @JsonProperty("admin")
    ADMIN;

    public static Role fromRoleString(String roleString) {
        if (!roleString.startsWith("ROLE_")) {
            throw new IllegalArgumentException();
        }
        try {
            return Role.valueOf(roleString.substring(5));
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }
}
