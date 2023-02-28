package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.SessionCookie;
import org.dev.toptenplaylist.model.UserCredentials;

public interface AuthenticationService {
    public SessionCookie login(String sessionToken, UserCredentials userCredentials);
    public void logout(String sessionToken);
}
