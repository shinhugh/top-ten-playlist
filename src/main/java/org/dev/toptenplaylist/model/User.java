package org.dev.toptenplaylist.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User implements Identifiable {
    private UUID id;
    private String email;
    private String password;
    private Set<Role> roles;
    private String displayName;

    public User() { }

    public User(User user) {
        id = user.id;
        email = user.email;
        password = user.password;
        roles = new HashSet<>(user.roles);
        displayName = user.displayName;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
