package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserProfileCrudRepository extends CrudRepository<UserProfile, String> {
    boolean existsByUserAccountId(String userAccountId); // TODO: Remove if not used
    Optional<UserProfile> findByUserAccountId(String userAccountId);
    Optional<UserProfile> findByName(String name);
    void deleteByUserAccountId(String userAccountId);
}
