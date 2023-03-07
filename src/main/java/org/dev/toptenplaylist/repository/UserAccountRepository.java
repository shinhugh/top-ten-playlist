package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserAccount;

public interface UserAccountRepository {
    UserAccount readById(String id);
    UserAccount readByName(String name);
    UserAccount readByNameCS(String name);
    String set(UserAccount userAccount);
    void deleteById(String id);
}
