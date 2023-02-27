package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;

public interface UserService {
    public User readByLoginName(String name);
    public void create(User user);
    public void updateByLoginName(String loginName, User user);
    public void deleteByLoginName(String name);
}
