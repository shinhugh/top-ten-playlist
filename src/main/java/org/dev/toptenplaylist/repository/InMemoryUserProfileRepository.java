package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserProfile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

// TODO: Replace with JPA-backed implementation of UserRepository
@Repository
public class InMemoryUserProfileRepository implements UserProfileRepository {
    private final HashMap<UUID, UserProfile> idToUserProfileMap = new HashMap<>();
    private final HashMap<UUID, UUID> userAccountIdToIdMap = new HashMap<>();
    private final HashMap<String, UUID> nameToIdMap = new HashMap<>();

    // TEST START
    public InMemoryUserProfileRepository() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UUID id = new UUID(0, 0);
        UUID userAccountId  = new UUID(0, 0);
        String name = "Dev";
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setUserAccountId(userAccountId);
        userProfile.setName(name);
        idToUserProfileMap.put(id, userProfile);
        userAccountIdToIdMap.put(userAccountId, id);
        nameToIdMap.put(name, id);
    }
    // TEST FINISH

    @Override
    public UserProfile readByUserAccountId(UUID userAccountId) {
        UserProfile userProfile = idToUserProfileMap.get(userAccountIdToIdMap.get(userAccountId));
        if (userProfile == null) {
            throw new NoSuchElementException();
        }
        return new UserProfile(userProfile);
    }

    @Override
    public UserProfile readByName(String name) {
        UserProfile userProfile = idToUserProfileMap.get(nameToIdMap.get(name));
        if (userProfile == null) {
            throw new NoSuchElementException();
        }
        return new UserProfile(userProfile);
    }

    @Override
    public void set(UserProfile userProfile) {
        UUID id = userProfile.getId();
        UUID userAccountId = userProfile.getUserAccountId();
        String name = userProfile.getName();
        if (userAccountId == null || name == null) {
            throw new IllegalArgumentException();
        }
        if (id == null) {
            id = generateId();
            userProfile.setId(id);
        }
        else {
            if (userAccountIdToIdMap.containsKey(userAccountId) && userAccountIdToIdMap.get(userAccountId) != id) {
                throw new IllegalArgumentException();
            }
            if (nameToIdMap.containsKey(name) && nameToIdMap.get(name) != id) {
                throw new IllegalArgumentException();
            }
            UserProfile oldUserProfile = idToUserProfileMap.get(id);
            if (oldUserProfile != null) {
                userAccountIdToIdMap.remove(oldUserProfile.getUserAccountId());
                nameToIdMap.remove(oldUserProfile.getName());
            }
        }
        idToUserProfileMap.put(id, new UserProfile(userProfile)); // TODO: Does Map.put() store reference?
        userAccountIdToIdMap.put(userAccountId, id);
        nameToIdMap.put(name, id);
    }

    @Override
    public void deleteByUserAccountId(UUID userAccountId) {
        UUID id = userAccountIdToIdMap.get(userAccountId);
        if (id == null) {
            throw new NoSuchElementException();
        }
        userAccountIdToIdMap.remove(userAccountId);
        nameToIdMap.remove(idToUserProfileMap.get(id).getName());
        idToUserProfileMap.remove(id);
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (idToUserProfileMap.containsKey(id)) {
            id  = UUID.randomUUID();
        }
        return id;
    }
}
