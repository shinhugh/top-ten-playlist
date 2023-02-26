package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.Role;
import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public Set<User> readAll(boolean hidePassword) {
        Set<User> set = userRepository.readAll();
        if (hidePassword) {
            set.forEach(user -> user.setPassword(null));
        }
        return set;
    }

    @Override
    public User readById(UUID id, boolean hidePassword) {
        User user;
        try {
            user = userRepository.readById(id);
        }
        catch (NoSuchElementException ex) {
            throw new NoSuchElementException("A user with the provided ID does not exist");
        }
        if (hidePassword) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public User readByEmail(String email, boolean hidePassword) {
        User user;
        try {
            user = userRepository.readByEmail(email);
        }
        catch (NoSuchElementException ex) {
            throw new NoSuchElementException("A user with the provided email address does not exist");
        }
        if (hidePassword) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public UUID create(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (email == null || password == null || user.getDisplayName() == null) {
            throw new IllegalArgumentException("An email address, display name, and password must be provided");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("A user already exists with the provided email address");
        }
        UUID id = generateId();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.set(user);
        return id;
    }

    @Override
    public void update(UUID id, User user) {
        User existing;
        try {
            existing = userRepository.readById(id);
        }
        catch (NoSuchElementException ex) {
            throw new NoSuchElementException("A user with the provided ID does not exist");
        }
        String newEmail = user.getEmail();
        String newPassword = user.getPassword();
        Set<Role> newRoles = user.getRoles();
        String newDisplayName = user.getDisplayName();
        if (newEmail != null && !newEmail.equals(existing.getEmail())) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("A user already exists with the provided email address");
            }
            existing.setEmail(newEmail);
        }
        if (newPassword != null) {
            existing.setPassword(passwordEncoder.encode(newPassword));
        }
        if (newRoles != null) {
            existing.setRoles(newRoles);
        }
        if (newDisplayName != null) {
            existing.setDisplayName(newDisplayName);
        }
        userRepository.set(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("A user with the provided ID does not exist");
        }
        userRepository.deleteById(id);
    }

    private UUID generateId() {
        UUID id = UUID.randomUUID();
        while (userRepository.existsById(id)) {
            id = UUID.randomUUID();
        }
        return id;
    }
}
