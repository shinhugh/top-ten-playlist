package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.UserProfile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return userProfileCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public UserProfile readByUserAccountId(String userAccountId) {
        if (userAccountId == null) {
            throw new IllegalArgumentException();
        }
        return userProfileCrudRepository.findByUserAccountId(userAccountId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public UserProfile readByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        return userProfileCrudRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String set(UserProfile userProfile) {
        if (userProfile == null || userProfile.getUserAccountId() == null || userProfile.getName() == null) {
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
        catch (DataIntegrityViolationException ex) {
            throw new ElementAlreadyExistsException();
        }
        return id;
    }

    @Override
    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        try {
            userProfileCrudRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ignored) { }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        while (userProfileCrudRepository.existsById(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
