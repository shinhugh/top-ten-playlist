package org.dev.toptenplaylist.model;

public class User {
    private final UserAccount userAccount;
    private final UserProfile userProfile;

    public User(UserAccount userAccount, UserProfile userProfile) {
        this.userAccount = userAccount;
        this.userProfile = userProfile;
    }

    public String getLoginName() {
        return userAccount.getName();
    }

    public void setLoginName(String loginName) {
        userAccount.setName(loginName);
    }

    public String getPasswordHash() {
        return userAccount.getPasswordHash();
    }

    public void setPasswordHash(String passwordHash) {
        userAccount.setPasswordHash(passwordHash);
    }

    public String getPublicName() {
        return userProfile.getName();
    }

    public void setPublicName(String publicName) {
        userProfile.setName(publicName);
    }
}
