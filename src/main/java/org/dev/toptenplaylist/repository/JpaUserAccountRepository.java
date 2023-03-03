package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserAccount;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaUserAccountRepository implements UserAccountRepository {
    private final UserAccountCrudRepository userAccountCrudRepository;

    public JpaUserAccountRepository(UserAccountCrudRepository userAccountCrudRepository) {
        this.userAccountCrudRepository = userAccountCrudRepository;
    }

    @Override
    public UserAccount readById(String id) {
        try {
            return userAccountCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserAccount readByName(String name) {
        try {
            return userAccountCrudRepository.findByName(name).orElseThrow(NoSuchElementException::new);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String set(UserAccount userAccount) {
        if (userAccount == null || userAccount.getName() == null || userAccount.getPasswordHash() == null) {
            throw new IllegalArgumentException();
        }
        String id = userAccount.getId();
        if (id == null) {
            id = generateId();
            userAccount.setId(id);
        }
        else {
            try {
                UUID.fromString(id);
            }
            catch (java.lang.IllegalArgumentException ex) {
                throw new IllegalArgumentException();
            }
        }
        try {
            userAccountCrudRepository.save(userAccount);
        }
        catch (DataIntegrityViolationException ex) {
            throw new ElementAlreadyExistsException();
        }
        return id;
    }

    @Override
    public void deleteById(String id) {
        try {
            userAccountCrudRepository.deleteById(id);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            throw new IllegalArgumentException();
        }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        while (userAccountCrudRepository.existsById(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
