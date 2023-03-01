package org.dev.toptenplaylist.model;

import java.util.UUID;

public class Session {
    private String token;
    private UUID userAccountId;
    private long expiration;

    public Session() { }

    public Session(String token, UUID userAccountId, long expiration) {
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

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(UUID userAccountId) {
        this.userAccountId = userAccountId;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
