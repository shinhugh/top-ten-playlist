package org.dev.toptenplaylist.model;

import java.util.Date;
import java.util.UUID;

public class Session {
    private String token;
    private UUID userAccountId;
    private Date expiration;

    public Session() { }

    public Session(String token, UUID userAccountId, Date expiration) {
        this.token = token;
        this.userAccountId = userAccountId;
        this.expiration = expiration;
    }

    public Session(Session session) {
        token = session.token;
        userAccountId = session.userAccountId;
        expiration = new Date(session.expiration.getTime());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(UUID userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
