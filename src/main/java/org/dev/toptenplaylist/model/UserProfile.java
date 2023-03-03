package org.dev.toptenplaylist.model;

public class UserProfile implements Identifiable {
    private String id;
    private String userAccountId;
    private String name;

    public UserProfile() { }

    public UserProfile(UserProfile userProfile) {
        id = userProfile.id;
        userAccountId = userProfile.userAccountId;
        name = userProfile.name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
