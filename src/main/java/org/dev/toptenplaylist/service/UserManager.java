package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.SongListContainer;
import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.model.UserProfile;
import org.dev.toptenplaylist.repository.*;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final SessionRepository sessionRepository;
    private final SongListContainerRepository songListContainerRepository;
    private final SongListEntryRepository songListEntryRepository;
    private final SecureHashService secureHashService;

    public UserManager(UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository, SessionRepository sessionRepository, SongListContainerRepository songListContainerRepository, SongListEntryRepository songListEntryRepository, SecureHashService secureHashService) {
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
        this.sessionRepository =  sessionRepository;
        this.songListContainerRepository = songListContainerRepository;
        this.songListEntryRepository = songListEntryRepository;
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
        if (user == null || (user.getLoginName() == null && user.getPassword() == null && user.getPublicName() == null)) {
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
                UserProfile publicNameUserProfile = userProfileRepository.readByName(user.getPublicName());
                if (!publicNameUserProfile.getId().equals(id)) {
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
        if (user == null || (user.getLoginName() == null && user.getPassword() == null && user.getPublicName() == null)) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount = userAccountRepository.readById(activeUserAccountId);
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        if (user.getLoginName() != null) {
            try {
                UserAccount loginNameUserAccount = userAccountRepository.readByName(user.getLoginName());
                if (!loginNameUserAccount.getId().equals(activeUserAccountId)) {
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
                UserProfile publicNameUserProfile = userProfileRepository.readByName(user.getPublicName());
                if (!publicNameUserProfile.getId().equals(userProfile.getId())) {
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
        try {
            SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(id);
            songListContainerRepository.deleteById(songListContainer.getId());
            songListEntryRepository.deleteBySongListContainerId(songListContainer.getId());
        }
        catch (NoSuchElementException ignored) { }
    }

    @Override
    public void deleteByActiveUserAccountId(String activeUserAccountId) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        userProfileRepository.deleteById(userProfile.getId());
        userAccountRepository.deleteById(activeUserAccountId);
        sessionRepository.deleteByUserAccountId(activeUserAccountId);
        try {
            SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(userProfile.getId());
            songListContainerRepository.deleteById(songListContainer.getId());
            songListEntryRepository.deleteBySongListContainerId(songListContainer.getId());
        }
        catch (NoSuchElementException ignored) { }
    }
}
