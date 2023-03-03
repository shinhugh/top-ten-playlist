package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserProfile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaUserProfileRepository implements UserProfileRepository {
    private final UserProfileCrudRepository userProfileCrudRepository;

    public JpaUserProfileRepository(UserProfileCrudRepository userProfileCrudRepository) {
        this.userProfileCrudRepository =  userProfileCrudRepository;
    }

    @Override
    public UserProfile readById(String id) {
        try {
            return userProfileCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserProfile readByUserAccountId(String userAccountId) {
        try {
            return userProfileCrudRepository.findByUserAccountId(userAccountId).orElseThrow(NoSuchElementException::new);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserProfile readByName(String name) {
        try {
            return userProfileCrudRepository.findByName(name).orElseThrow(NoSuchElementException::new);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String set(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException();
        }
        String id = userProfile.getId();
        if (id == null) {
            id = generateId();
            userProfile.setId(id);
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
            userProfileCrudRepository.save(userProfile);
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
            userProfileCrudRepository.deleteById(id);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteByUserAccountId(String userAccountId) {
        try {
            userProfileCrudRepository.deleteByUserAccountId(userAccountId);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        while (userProfileCrudRepository.existsById(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
