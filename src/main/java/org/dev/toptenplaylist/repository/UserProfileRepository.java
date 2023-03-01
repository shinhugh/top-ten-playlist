package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserProfile;

import java.util.UUID;

public interface UserProfileRepository {
    UserProfile readById(UUID id);
    UserProfile readByUserAccountId(UUID userAccountId);
    UserProfile readByName(String name);
    UUID set(UserProfile userProfile);
    void deleteById(UUID id);
    void deleteByUserAccountId(UUID userAccountId);
}
