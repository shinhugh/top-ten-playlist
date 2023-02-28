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

import java.util.UUID;

@Service
public class UserManager implements UserService {
    private final SessionRepository sessionRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final IdentificationService identificationService;
    private final SecureHashService secureHashService;

    public UserManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository, IdentificationService identificationService, SecureHashService secureHashService) {
        this.sessionRepository =  sessionRepository;
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.identificationService = identificationService;
        this.secureHashService = secureHashService;
    }

    @Override
    public User readByLoginName(String sessionToken, String loginName) {
        if (sessionToken == null) {
            throw new AccessDeniedException();
        }
        UserAccount activeUserAccount = identificationService.identifyCurrentUser(sessionToken);
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
        if (!activeUserAccount.getId().equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readByUserAccountId(userAccount.getId());
        }
        catch (NoSuchElementException ex) {
            throw new RuntimeException();
        }
        return new User(userAccount, userProfile);
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
        UserAccount userAccount = new UserAccount();
        userAccount.setName(user.getLoginName());
        userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        UUID userAccountId;
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
    public void updateByLoginName(String sessionToken, String loginName, User user) {
        if (sessionToken == null) {
            throw new AccessDeniedException();
        }
        UserAccount activeUserAccount = identificationService.identifyCurrentUser(sessionToken);
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
        if (!activeUserAccount.getId().equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        if (user == null) {
            throw new IllegalArgumentException();
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileRepository.readByUserAccountId(userAccount.getId());
        }
        catch (NoSuchElementException ex) {
            throw new RuntimeException();
        }
        if (user.getLoginName() != null) {
            try {
                userAccountRepository.readByName(user.getLoginName());
                throw new ElementAlreadyExistsException();
            }
            catch (NoSuchElementException ignored) { }
            userAccount.setName(user.getLoginName());
        }
        if (user.getPassword() != null) {
            userAccount.setPasswordHash(secureHashService.hash(user.getPassword()));
        }
        if (user.getPublicName() != null) {
            try {
                userProfileRepository.readByName(user.getPublicName());
                throw new ElementAlreadyExistsException();
            }
            catch (NoSuchElementException ignored) { }
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
    public void deleteByLoginName(String sessionToken, String loginName) {
        if (sessionToken == null) {
            throw new AccessDeniedException();
        }
        UserAccount activeUserAccount = identificationService.identifyCurrentUser(sessionToken);
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
        if (!activeUserAccount.getId().equals(userAccount.getId())) {
            throw new AccessDeniedException();
        }
        try {
            userProfileRepository.deleteByUserAccountId(userAccount.getId());
            userAccountRepository.deleteByName(loginName);
        }
        catch (NoSuchElementException ex) {
            throw new RuntimeException();
        }
        sessionRepository.deleteByUserAccountId(userAccount.getId());
    }
}
