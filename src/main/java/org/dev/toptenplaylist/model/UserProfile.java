package org.dev.toptenplaylist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserProfile implements Identifiable {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String userAccountId;
    @Column(nullable = false, unique = true)
    private String name;

    public UserProfile() { }

    public UserProfile(String userAccountId, String name) {
        this.userAccountId = userAccountId;
        this.name = name;
    }

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
