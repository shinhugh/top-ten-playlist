package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.AuthenticationResult;
import org.dev.toptenplaylist.model.LoginCredentials;

public interface AuthenticationService {
    public AuthenticationResult login(String sessionToken, LoginCredentials loginCredentials);
    public AuthenticationResult logout(String sessionToken);
}
