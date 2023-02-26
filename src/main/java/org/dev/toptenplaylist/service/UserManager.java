package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.dev.toptenplaylist.repository.UserProfileRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserManager implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManager(UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public User readByName(String name) {
        // TODO
        return null;
    }

    @Override
    public UUID create(User user) {
        // TODO
        return null;
    }

    @Override
    public void update(User user) {
        // TODO
    }

    @Override
    public void deleteByName(String name) {
        // TODO
    }
}
