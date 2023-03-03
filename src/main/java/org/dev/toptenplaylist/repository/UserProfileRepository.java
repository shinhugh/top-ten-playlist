package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserProfile;

public interface UserProfileRepository {
    UserProfile readById(String id);
    UserProfile readByUserAccountId(String userAccountId);
    UserProfile readByName(String name);
    String set(UserProfile userProfile);
    void deleteById(String id);
    void deleteByUserAccountId(String userAccountId);
}
