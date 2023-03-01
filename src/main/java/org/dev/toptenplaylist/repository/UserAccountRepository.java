package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserAccount;

import java.util.UUID;

public interface UserAccountRepository {
    UserAccount readById(UUID id);
    UserAccount readByName(String name);
    UUID set(UserAccount userAccount);
    void deleteById(UUID id);
    void deleteByName(String name);
}
