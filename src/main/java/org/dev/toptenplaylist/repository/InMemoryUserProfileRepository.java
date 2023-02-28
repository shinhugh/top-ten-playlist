package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO: Replace with JPA-backed implementation of UserProfileRepository
@Repository
public class InMemoryUserProfileRepository implements UserProfileRepository {
    private final Map<UUID, UserProfile> idToUserProfileMap = new HashMap<>();
    private final Map<UUID, UUID> userAccountIdToIdMap = new HashMap<>();
    private final Map<String, UUID> nameToIdMap = new HashMap<>();

    // TEST START
    public InMemoryUserProfileRepository() {
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
        if (userAccountId == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile = idToUserProfileMap.get(userAccountIdToIdMap.get(userAccountId));
        if (userProfile == null) {
            throw new NoSuchElementException();
        }
        return new UserProfile(userProfile);
    }

    @Override
    public UserProfile readByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile = idToUserProfileMap.get(nameToIdMap.get(name));
        if (userProfile == null) {
            throw new NoSuchElementException();
        }
        return new UserProfile(userProfile);
    }

    @Override
    public UUID set(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException();
        }
        UserProfile newUserProfile = new UserProfile(userProfile);
        UUID id = newUserProfile.getId();
        UUID userAccountId = newUserProfile.getUserAccountId();
        String name = newUserProfile.getName();
        if (userAccountId == null || name == null) {
            throw new IllegalArgumentException();
        }
        if (id == null) {
            id = generateId();
            newUserProfile.setId(id);
        }
        else {
            if (userAccountIdToIdMap.containsKey(userAccountId) && userAccountIdToIdMap.get(userAccountId) != id) {
                throw new ElementAlreadyExistsException();
            }
            if (nameToIdMap.containsKey(name) && nameToIdMap.get(name) != id) {
                throw new ElementAlreadyExistsException();
            }
            UserProfile oldUserProfile = idToUserProfileMap.get(id);
            if (oldUserProfile != null) {
                userAccountIdToIdMap.remove(oldUserProfile.getUserAccountId());
                nameToIdMap.remove(oldUserProfile.getName());
            }
        }
        idToUserProfileMap.put(id, newUserProfile);
        userAccountIdToIdMap.put(userAccountId, id);
        nameToIdMap.put(name, id);
        return id;
    }

    @Override
    public void deleteByUserAccountId(UUID userAccountId) {
        if (userAccountId == null) {
            throw new IllegalArgumentException();
        }
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
