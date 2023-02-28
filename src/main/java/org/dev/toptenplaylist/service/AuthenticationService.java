package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.UserCredentials;

public interface AuthenticationService {
    public String login(String sessionToken, UserCredentials userCredentials);
    public void logout(String sessionToken);
}
