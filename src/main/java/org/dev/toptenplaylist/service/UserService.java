package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

public interface UserService {
    public User readByLoginName(String sessionToken, String name);
    public void create(User user);
    public void updateByLoginName(String sessionToken, String loginName, User user);
    public void deleteByLoginName(String sessionToken, String name);
}
