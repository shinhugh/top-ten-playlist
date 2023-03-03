package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserAccount;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
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
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserAccount readByName(String name) {
        try {
            return userAccountCrudRepository.findByName(name).orElseThrow(NoSuchElementException::new);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String set(UserAccount userAccount) {
        if (userAccount == null) {
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
        catch (java.lang.IllegalArgumentException | JpaSystemException ex) {
            throw new IllegalArgumentException();
        }
        catch (DataIntegrityViolationException ex) {
            System.out.println("@@ DEBUG: " + ex.getClass() + " | " + ex.getMessage()); // DEBUG
//            if () { // TODO: If non-null rule is violated
//                throw new IllegalArgumentException();
//            }
//            else { // TODO: If unique rule is violated
//                throw new ElementAlreadyExistsException();
//            }
            throw new ElementAlreadyExistsException();
        }
        return id;
    }

    @Override
    public void deleteById(String id) {
        try {
            userAccountCrudRepository.deleteById(id);
        }
        catch (java.lang.IllegalArgumentException ex) {
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
