package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryUserProfileRepository implements UserProfileRepository {
    private final Map<String, UserProfile> idToUserProfileMap = new HashMap<>();
    private final Map<String, String> userAccountIdToIdMap = new HashMap<>();
    private final Map<String, String> nameToIdMap = new HashMap<>();

    // TEST START
    public InMemoryUserProfileRepository() {
        String id = new UUID(1, 2).toString();
        String userAccountId  = new UUID(1, 2).toString();
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
    public UserProfile readById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile = idToUserProfileMap.get(id);
        if (userProfile == null) {
            throw new NoSuchElementException();
        }
        return new UserProfile(userProfile);
    }

    @Override
    public UserProfile readByUserAccountId(String userAccountId) {
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
    public String set(UserProfile userProfile) {
        if (userProfile == null || userProfile.getUserAccountId() == null || userProfile.getName() == null) {
            throw new IllegalArgumentException();
        }
        UserProfile newUserProfile = new UserProfile(userProfile);
        String id = newUserProfile.getId();
        String userAccountId = newUserProfile.getUserAccountId();
        String name = newUserProfile.getName();
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
    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile = idToUserProfileMap.get(id);
        if (userProfile == null) {
            throw new NoSuchElementException();
        }
        userAccountIdToIdMap.remove(userProfile.getUserAccountId());
        nameToIdMap.remove(userProfile.getName());
        idToUserProfileMap.remove(id);
    }

    @Override
    public void deleteByUserAccountId(String userAccountId) {
        if (userAccountId == null) {
            throw new IllegalArgumentException();
        }
        String id = userAccountIdToIdMap.get(userAccountId);
        if (id == null) {
            throw new NoSuchElementException();
        }
        userAccountIdToIdMap.remove(userAccountId);
        nameToIdMap.remove(idToUserProfileMap.get(id).getName());
        idToUserProfileMap.remove(id);
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        while (idToUserProfileMap.containsKey(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
