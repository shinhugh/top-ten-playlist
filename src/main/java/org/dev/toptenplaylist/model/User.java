package org.dev.toptenplaylist.model;

public class User {
    private String loginName;
    private String password;
    private String publicName;

    public User() { }

    public User(UserAccount userAccount, UserProfile userProfile) {
        loginName = userAccount.getName();
        publicName = userProfile.getName();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }
}
