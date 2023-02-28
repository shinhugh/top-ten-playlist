package org.dev.toptenplaylist.model;

public class SessionCookie {
    private String token;
    private int maxAge;

    public SessionCookie() { }

    public SessionCookie(String token, int maxAge) {
        this.token = token;
        this.maxAge = maxAge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
