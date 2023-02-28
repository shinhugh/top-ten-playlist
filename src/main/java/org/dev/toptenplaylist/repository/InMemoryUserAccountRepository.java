package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.service.SecureHashManager;
import org.dev.toptenplaylist.service.SecureHashService;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO: Replace with JPA-backed implementation of UserAccountRepository
@Repository
public class InMemoryUserAccountRepository implements UserAccountRepository {
    private final Map<UUID, UserAccount> idToUserAccountMap = new HashMap<>();
    private final Map<String, UUID> nameToIdMap = new HashMap<>();

    // TEST START
    public InMemoryUserAccountRepository() {
        SecureHashService secureHashService = new SecureHashManager();
        UUID id = new UUID(0, 0);
        String name = "dev";
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        userAccount.setName(name);
        userAccount.setPasswordHash(secureHashService.hash("dev"));
        idToUserAccountMap.put(id, userAccount);
        nameToIdMap.put(name, id);
    }
    // TEST FINISH

    @Override
    public UserAccount readById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount = idToUserAccountMap.get(id);
        if (userAccount == null) {
            throw new NoSuchElementException();
        }
        return new UserAccount(userAccount);
    }

    @Override
    public UserAccount readByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount = idToUserAccountMap.get(nameToIdMap.get(name));
        if (userAccount == null) {
            throw new NoSuchElementException();
        }
        return new UserAccount(userAccount);
    }

    @Override
    public UUID set(UserAccount userAccount) {
        if (userAccount == null || userAccount.getName() == null) {
            throw new IllegalArgumentException();
        }
        UserAccount newUserAccount = new UserAccount(userAccount);
        UUID id = newUserAccount.getId();
        String name = newUserAccount.getName();
        if (id == null) {
            id = generateId();
            newUserAccount.setId(id);
        }
        else {
            if (nameToIdMap.containsKey(name) && nameToIdMap.get(name) != id) {
                throw new ElementAlreadyExistsException();
            }
            UserAccount oldUserAccount = idToUserAccountMap.get(id);
            if (oldUserAccount != null) {
                nameToIdMap.remove(oldUserAccount.getName());
            }
        }
        idToUserAccountMap.put(id, newUserAccount);
        nameToIdMap.put(name, id);
        return id;
    }

    @Override
    public void deleteByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        UUID id = nameToIdMap.get(name);
        if (id == null) {
            throw new NoSuchElementException();
        }
        nameToIdMap.remove(name);
        idToUserAccountMap.remove(id);
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (idToUserAccountMap.containsKey(id)) {
            id  = UUID.randomUUID();
        }
        return id;
    }
}
