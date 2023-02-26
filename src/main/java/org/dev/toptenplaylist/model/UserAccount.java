package org.dev.toptenplaylist.model;

import java.util.UUID;

public class UserAccount implements Identifiable {
    private UUID id;
    private String name;
    private String passwordHash;

    public UserAccount() { }

    public UserAccount(UserAccount userAccount) {
        id = userAccount.id;
        name = userAccount.name;
        passwordHash = userAccount.passwordHash;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
