package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.UserCredentials;

public interface AuthService {
    public void login(UserCredentials userCredentials);
    public void logout();
}
