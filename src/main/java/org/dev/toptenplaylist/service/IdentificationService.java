package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.UserAccount;

public interface IdentificationService {
    public UserAccount identifyCurrentUser(String sessionToken);
}
