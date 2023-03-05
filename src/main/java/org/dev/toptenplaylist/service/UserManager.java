package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.model.UserProfile;
import org.dev.toptenplaylist.repository.SessionRepository;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.dev.toptenplaylist.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final SessionRepository sessionRepository;
    private final SecureHashService secureHashService;

    public UserManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository, SecureHashService secureHashService) {
        this.sessionRepository =  sessionRepository;
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.secureHashService = secureHashService;
    }

    @Override
    public User readById(String activeUserAccountId, String id) {
        UserProfile userProfile = userProfileRepository.readById(id);
        UserAccount userAccount = userAccountRepository.readById(userProfile.getUserAccountId());
        User user = new User(userAccount, userProfile);
        if (!userAccount.getId().equals(activeUserAccountId)) {
            user.setLoginName(null);
        }
        return user;
    }

    @Override
    public User readByActiveUserAccountId(String activeUserAccountId) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserAccount userAccount = userAccountRepository.readById(activeUserAccountId);
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        return new User(userAccount, userProfile);
    }

    @Override
    public User readByPublicName(String activeUserAccountId, String publicName) {
        UserProfile userProfile = userProfileRepository.readByName(publicName);
        UserAccount userAccount = userAccountRepository.readById(userProfile.getUserAccountId());
        User user = new User(userAccount, userProfile);
        if (!userAccount.getId().equals(activeUserAccountId)) {
            user.setLoginName(null);
        }
        return user;
    }

    @Override
    public void create(User user) {
        if (user == null || user.getLoginName() == null || user.getPassword() == null || user.getPublicName() == null) {
            throw new IllegalArgumentException();
        }
        try {
            userAccountRepository.readByName(user.getLoginName());
            throw new ElementAlreadyExistsException();
        }
        catch (NoSuchElementException ignored) { }
        try {
            userProfileRepository.readByName(user.getPublicName());
            throw new ElementAlreadyExistsException();
        }
        catch (NoSuchElementException ignored) { }
        UserAccount userAccount = new UserAccount(user.getLoginName(), secureHashService.hash(user.getPassword()));
        String userAccountId = userAccountRepository.set(userAccount);
        UserProfile userProfile = new UserProfile(userAccountId, user.getPublicName());
        userProfileRepository.set(userProfile);
    }

    @Override
    public void updateById(String activeUserAccountId, String id, User user) {
        UserProfile userProfile = userProfileRepository.readById(id);
        if (!userProfile.getUserAccountId().equals(activeUserAccountId)) {
            throw new AccessDeniedException();
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount = userAccountRepository.readById(userProfile.getUserAccountId());
        if (user.getLoginName() != null) {
            try {
                UserAccount loginNameUserAccount = userAccountRepository.readByName(user.getLoginName());
                if (!loginNameUserAccount.getId().equals(userAccount.getId())) {
                    throw new ElementAlreadyExistsException();
                }
            }
            catch (NoSuchElementException ignored) { }
            userAccount.setName(user.getLoginName());
        }
        if (user.getPassword() != null) {
            userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        }
        if (user.getPublicName() != null) {
            try {
                UserProfile publicNameUserAccount = userProfileRepository.readByName(user.getPublicName());
                if (!publicNameUserAccount.getId().equals(userProfile.getId())) {
                    throw new ElementAlreadyExistsException();
                }
            }
            catch (NoSuchElementException ignored) { }
            userProfile.setName(user.getPublicName());
        }
        userAccountRepository.set(userAccount);
        userProfileRepository.set(userProfile);
    }

    @Override
    public void updateByActiveUserAccountId(String activeUserAccountId, User user) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount = userAccountRepository.readById(activeUserAccountId);
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        if (user.getLoginName() != null) {
            try {
                UserAccount loginNameUserAccount = userAccountRepository.readByName(user.getLoginName());
                if (!loginNameUserAccount.getId().equals(userAccount.getId())) {
                    throw new ElementAlreadyExistsException();
                }
            }
            catch (NoSuchElementException ignored) { }
            userAccount.setName(user.getLoginName());
        }
        if (user.getPassword() != null) {
            userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        }
        if (user.getPublicName() != null) {
            try {
                UserProfile publicNameUserAccount = userProfileRepository.readByName(user.getPublicName());
                if (!publicNameUserAccount.getId().equals(userProfile.getId())) {
                    throw new ElementAlreadyExistsException();
                }
            }
            catch (NoSuchElementException ignored) { }
            userProfile.setName(user.getPublicName());
        }
        userAccountRepository.set(userAccount);
        userProfileRepository.set(userProfile);
    }

    @Override
    public void deleteById(String activeUserAccountId, String id) {
        UserProfile userProfile = userProfileRepository.readById(id);
        if (!userProfile.getUserAccountId().equals(activeUserAccountId)) {
            throw new AccessDeniedException();
        }
        userProfileRepository.deleteById(id);
        userAccountRepository.deleteById(userProfile.getUserAccountId());
        sessionRepository.deleteByUserAccountId(userProfile.getUserAccountId());
        // TODO: Delete user's song list
    }

    @Override
    public void deleteByActiveUserAccountId(String activeUserAccountId) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        userProfileRepository.deleteByUserAccountId(activeUserAccountId);
        userAccountRepository.deleteById(activeUserAccountId);
        sessionRepository.deleteByUserAccountId(activeUserAccountId);
        // TODO: Delete user's song list
    }
}
