package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.model.UserProfile;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.dev.toptenplaylist.repository.UserProfileRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
    public User readByLoginName(String loginName) {
        UserAccount userAccount = userAccountRepository.readByName(loginName);
        UserProfile userProfile = userProfileRepository.readByUserAccountId(userAccount.getId());
        return new User(userAccount, userProfile);
    }

    @Override
    public void create(User user) {
        try {
            userAccountRepository.readByName(user.getLoginName());
            throw new IllegalArgumentException();
        }
        catch (NoSuchElementException ex) { }
        try {
            userProfileRepository.readByName(user.getPublicName());
            throw new IllegalArgumentException();
        }
        catch (NoSuchElementException ex) { }
        UserAccount userAccount = new UserAccount();
        userAccount.setName(user.getLoginName());
        userAccount.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        UUID userAccountId = userAccountRepository.set(userAccount);
        UserProfile userProfile = new UserProfile();
        userProfile.setUserAccountId(userAccountId);
        userProfile.setName(user.getPublicName());
        userProfileRepository.set(userProfile);
    }

    @Override
    public void updateByLoginName(String loginName, User user) {
        // TODO: Handle update described as a changelist
        UserAccount userAccount = userAccountRepository.readByName(loginName);
        userAccount.setName(user.getLoginName());
        userAccount.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        userAccountRepository.set(userAccount);
        UserProfile userProfile = userProfileRepository.readByUserAccountId(userAccount.getId());
        userProfile.setName(user.getPublicName());
        userProfileRepository.set(userProfile);
    }

    @Override
    public void deleteByLoginName(String loginName) {
        userProfileRepository.deleteByUserAccountId(userAccountRepository.readByName(loginName).getId());
        userAccountRepository.deleteByName(loginName);
    }
}
