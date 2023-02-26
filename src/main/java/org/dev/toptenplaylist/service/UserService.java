package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

import java.util.Set;
import java.util.UUID;

public interface UserService {
    public Set<User> readAll(boolean hidePassword);
    public User readById(UUID id, boolean hidePassword);
    public User readByEmail(String email, boolean hidePassword);
    public UUID create(User user);
    public void update(UUID id, User user);
    public void delete(UUID id);
}
