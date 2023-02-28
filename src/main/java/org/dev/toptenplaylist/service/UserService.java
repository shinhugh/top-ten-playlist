package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

import java.util.UUID;

public interface UserService {
    public User readByLoginName(UUID activeUserAccountId, String name);
    public void create(User user);
    public void updateByLoginName(UUID activeUserAccountId, String loginName, User user);
    public void deleteByLoginName(UUID activeUserAccountId, String name);
}
