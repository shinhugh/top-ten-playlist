package org.dev.toptenplaylist.model;

import java.util.UUID;

public class UserProfile implements Identifiable {
    private UUID id;
    private UUID userAccountId;
    private String name;

    public UserProfile() { }

    public UserProfile(UserProfile userProfile) {
        id = userProfile.id;
        userAccountId = userProfile.userAccountId;
        name = userProfile.name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(UUID userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
