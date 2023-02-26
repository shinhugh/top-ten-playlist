package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserAccount;

import java.util.UUID;

public interface UserAccountRepository {
    UserAccount readById(UUID id); // TODO: Necessary?
    UserAccount readByName(String name);
    void set(UserAccount userAccount);
    void deleteByName(String name);
}
