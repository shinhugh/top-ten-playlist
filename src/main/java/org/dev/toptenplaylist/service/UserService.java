package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

public interface UserService {
    public User readById(String activeUserAccountId, String id);
    public User readByActiveUserAccountId(String activeUserAccountId);
    public User readByPublicName(String activeUserAccountId, String publicName);
    public void create(User user);
    public void updateById(String activeUserAccountId, String id, User user);
    public void updateByActiveUserAccountId(String activeUserAccountId, User user);
    public void deleteById(String activeUserAccountId, String id);
    public void deleteByActiveUserAccountId(String activeUserAccountId);
}
