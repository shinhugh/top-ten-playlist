package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

import java.util.UUID;

public interface UserService {
    public User readById(UUID activeUserAccountId, UUID id);
    public User readByLoginName(UUID activeUserAccountId, String loginName);
    public User readByPublicName(UUID activeUserAccountId, String publicName);
    public void create(User user);
    public void updateById(UUID activeUserAccountId, UUID id, User user);
    public void updateByLoginName(UUID activeUserAccountId, String loginName, User user);
    public void deleteById(UUID activeUserAccountId, UUID id);
    public void deleteByLoginName(UUID activeUserAccountId, String loginName);
}
