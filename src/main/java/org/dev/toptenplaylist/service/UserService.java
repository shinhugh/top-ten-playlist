package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.model.UserAccount;

import java.util.Set;
import java.util.UUID;

public interface UserService {
    public User readByName(String name);
    public UUID create(User user);
    public void update(User user);
    public void deleteByName(String name);
}
