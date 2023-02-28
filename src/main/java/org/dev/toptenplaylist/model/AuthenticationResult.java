package org.dev.toptenplaylist.model;

public class AuthenticationResult {
    private boolean shouldSetCookie;
    private String sessionToken;
    private int sessionCookieMaxAge;
    private RuntimeException exception;

    public AuthenticationResult() { }

    public AuthenticationResult(boolean shouldSetCookie, String sessionToken, int sessionCookieMaxAge, RuntimeException exception) {
        this.shouldSetCookie = shouldSetCookie;
        this.sessionToken = sessionToken;
        this.sessionCookieMaxAge = sessionCookieMaxAge;
        this.exception = exception;
    }

    public boolean isShouldSetCookie() {
        return shouldSetCookie;
    }

    public void setShouldSetCookie(boolean shouldSetCookie) {
        this.shouldSetCookie = shouldSetCookie;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public int getSessionCookieMaxAge() {
        return sessionCookieMaxAge;
    }

    public void setSessionCookieMaxAge(int sessionCookieMaxAge) {
        this.sessionCookieMaxAge = sessionCookieMaxAge;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }
}
