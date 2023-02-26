package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.User;

import java.util.Set;
import java.util.UUID;

public interface UserRepository {
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    Set<User> readAll();
    User readById(UUID id);
    User readByEmail(String email);
    void set(User user);
    void deleteById(UUID id);
    void deleteByEmail(String email);
}
