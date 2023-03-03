package org.dev.toptenplaylist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session {
    @Id
    private String token;
    @Column(nullable = false)
    private String userAccountId;
    private long expiration;

    public Session() { }

    public Session(String token, String userAccountId, long expiration) {
        this.token = token;
        this.userAccountId = userAccountId;
        this.expiration = expiration;
    }

    public Session(Session session) {
        token = session.token;
        userAccountId = session.userAccountId;
        expiration = session.expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
