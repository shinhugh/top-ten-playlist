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
    private final SessionRepository sessionRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final SecureHashService secureHashService;

    public UserManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository, SecureHashService secureHashService) {
        this.sessionRepository =  sessionRepository;
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.secureHashService = secureHashService;
    }

    @Override
    public User readById(String activeUserAccountId, String id) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (id == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readById(id);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readById(userProfile.getUserAccountId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        return new User(userAccount, userProfile);
    }

    @Override
    public User readByLoginName(String activeUserAccountId, String loginName) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (loginName == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readByName(loginName);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readByUserAccountId(userAccount.getId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        return new User(userAccount, userProfile);
    }

    @Override
    public User readByPublicName(String activeUserAccountId, String publicName) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (publicName == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readByName(publicName);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readById(userProfile.getUserAccountId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        return new User(userAccount, userProfile);
    }

    @Override
    public void create(User user) {
        if (user == null || user.getLoginName() == null || user.getPassword() == null || user.getPublicName() == null) {
            throw new IllegalArgumentException();
        }
        try {
            try {
                userAccountRepository.readByName(user.getLoginName());
            }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            throw new ElementAlreadyExistsException();
        }
        catch (NoSuchElementException ignored) { }
        try {
            try {
                userProfileRepository.readByName(user.getPublicName());
            }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            throw new ElementAlreadyExistsException();
        }
        catch (NoSuchElementException ignored) { }
        UserAccount userAccount = new UserAccount();
        userAccount.setName(user.getLoginName());
        userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        String userAccountId;
        try {
            userAccountId = userAccountRepository.set(userAccount);
        }
        catch (IllegalArgumentException | ElementAlreadyExistsException ex) {
            throw new RuntimeException();
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUserAccountId(userAccountId);
        userProfile.setName(user.getPublicName());
        try {
            userProfileRepository.set(userProfile);
        }
        catch (IllegalArgumentException  | ElementAlreadyExistsException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateById(String activeUserAccountId, String id, User user) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (id == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readById(id);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readById(userProfile.getUserAccountId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }
        if (user.getLoginName() != null) {
            UserAccount loginNameUserAccount = null;
            try {
                loginNameUserAccount = userAccountRepository.readByName(user.getLoginName());
            }
            catch (NoSuchElementException ignored) { }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            if (loginNameUserAccount != null && !loginNameUserAccount.getId().equals(userAccount.getId())) {
                throw new ElementAlreadyExistsException();
            }
            userAccount.setName(user.getLoginName());
        }
        if (user.getPassword() != null) {
            userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        }
        if (user.getPublicName() != null) {
            UserProfile publicNameUserAccount = null;
            try {
                publicNameUserAccount = userProfileRepository.readByName(user.getPublicName());
            }
            catch (NoSuchElementException ignored) { }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            if (publicNameUserAccount != null && !publicNameUserAccount.getId().equals(userProfile.getId())) {
                throw new ElementAlreadyExistsException();
            }
            userProfile.setName(user.getPublicName());
        }
        try {
            userAccountRepository.set(userAccount);
            userProfileRepository.set(userProfile);
        }
        catch (IllegalArgumentException | ElementAlreadyExistsException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateByLoginName(String activeUserAccountId, String loginName, User user) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (loginName == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readByName(loginName);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readByUserAccountId(userAccount.getId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (user.getLoginName() != null) {
            UserAccount loginNameUserAccount = null;
            try {
                loginNameUserAccount = userAccountRepository.readByName(user.getLoginName());
            }
            catch (NoSuchElementException ignored) { }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            if (loginNameUserAccount != null && !loginNameUserAccount.getId().equals(userAccount.getId())) {
                throw new ElementAlreadyExistsException();
            }
            userAccount.setName(user.getLoginName());
        }
        if (user.getPassword() != null) {
            userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        }
        if (user.getPublicName() != null) {
            UserProfile publicNameUserAccount = null;
            try {
                publicNameUserAccount = userProfileRepository.readByName(user.getPublicName());
            }
            catch (NoSuchElementException ignored) { }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            if (publicNameUserAccount != null && !publicNameUserAccount.getId().equals(userProfile.getId())) {
                throw new ElementAlreadyExistsException();
            }
            userProfile.setName(user.getPublicName());
        }
        try {
            userAccountRepository.set(userAccount);
            userProfileRepository.set(userProfile);
        }
        catch (IllegalArgumentException | ElementAlreadyExistsException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteById(String activeUserAccountId, String id) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (id == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readById(id);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userProfile.getUserAccountId())) {
            throw new AccessDeniedException();
        }
        try {
            userProfileRepository.deleteById(id);
            userAccountRepository.deleteById(userProfile.getUserAccountId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        try {
            sessionRepository.deleteByUserAccountId(userProfile.getUserAccountId());
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteByLoginName(String activeUserAccountId, String loginName) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (loginName == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readByName(loginName);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        if (!activeUserAccountId.equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        try {
            userProfileRepository.deleteByUserAccountId(userAccount.getId());
            userAccountRepository.deleteById(userAccount.getId());
        }
        catch (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        try {
            sessionRepository.deleteByUserAccountId(userAccount.getId());
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
    }
}
