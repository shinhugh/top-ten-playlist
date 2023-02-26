package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Role;
import org.dev.toptenplaylist.model.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final HashMap<UUID, User> userMap = new HashMap<>();
    private final HashMap<String, UUID> emailMap = new HashMap<>();

    // DEBUG
    public InMemoryUserRepository() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        {
            UUID id = UUID.randomUUID();
            String email = "admin@dev.org";
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setDisplayName("Admin");
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN)));
            userMap.put(id, user);
            emailMap.put(email, id);
        }
        {
            UUID id = UUID.randomUUID();
            String email = "user@dev.org";
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password"));
            user.setDisplayName("User");
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
            userMap.put(id, user);
            emailMap.put(email, id);
        }
    }
    // DEBUG

    @Override
    public boolean existsById(UUID id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return emailMap.containsKey(email);
    }

    @Override
    public Set<User> readAll() {
        Set<User> copySet = new HashSet<>();
        userMap.forEach((id, user) -> copySet.add(new User(user)));
        return copySet;
    }

    @Override
    public User readById(UUID id) {
        User user = userMap.get(id);
        if (user == null) {
            throw new NoSuchElementException();
        }
        return new User(user);
    }

    @Override
    public User readByEmail(String email) {
        User user = userMap.get(emailMap.get(email));
        if (user == null) {
            throw new NoSuchElementException();
        }
        return new User(user);
    }

    @Override
    public void set(User user) {
        UUID id = user.getId();
        String email = user.getEmail();
        if (id == null || email == null) {
            throw new IllegalArgumentException();
        }
        if (emailMap.containsKey(email) && emailMap.get(email) != id) {
            throw new IllegalArgumentException();
        }
        User oldUser = userMap.get(id);
        if (oldUser != null) {
            emailMap.remove(oldUser.getEmail());
        }
        emailMap.put(email, id);
        userMap.put(id, user);
    }

    @Override
    public void deleteById(UUID id) {
        User user = userMap.get(id);
        if (user == null) {
            return;
        }
        emailMap.remove(user.getEmail());
        userMap.remove(id);
    }

    @Override
    public void deleteByEmail(String email) {
        UUID id = emailMap.get(email);
        if (id == null) {
            return;
        }
        emailMap.remove(email);
        userMap.remove(id);
    }
}
