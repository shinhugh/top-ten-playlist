package org.dev.toptenplaylist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserAccount implements Identifiable {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String passwordHash;

    public UserAccount() { }

    public UserAccount(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public UserAccount(UserAccount userAccount) {
        id = userAccount.id;
        name = userAccount.name;
        passwordHash = userAccount.passwordHash;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
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
