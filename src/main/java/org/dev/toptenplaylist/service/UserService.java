package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

public interface UserService {
    public User readById(String activeUserAccountId, String id);
    public User readByLoginName(String activeUserAccountId, String loginName);
    public User readByPublicName(String activeUserAccountId, String publicName);
    public void create(User user);
    public void updateById(String activeUserAccountId, String id, User user);
    public void updateByLoginName(String activeUserAccountId, String loginName, User user);
    public void deleteById(String activeUserAccountId, String id);
    public void deleteByLoginName(String activeUserAccountId, String loginName);
}
