package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.AuthenticationResult;
import org.dev.toptenplaylist.model.LoginCredentials;
import org.dev.toptenplaylist.model.Session;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.repository.SessionRepository;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationManager implements AuthenticationService {
    private final SessionRepository sessionRepository;
    private final UserAccountRepository userAccountRepository;
    private final SecureHashService secureHashService;
    private final int sessionMaxDuration = 7200;

    public AuthenticationManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository, SecureHashService secureHashService) {
        this.sessionRepository = sessionRepository;
        this.userAccountRepository =  userAccountRepository;
        this.secureHashService = secureHashService;
    }

    @Override
    public AuthenticationResult login(String sessionToken, LoginCredentials loginCredentials) {
        AuthenticationResult result = new AuthenticationResult(false, null, 0, null);
        if (sessionToken != null) {
            Session requestSession = null;
            try {
                requestSession = sessionRepository.readByToken(sessionToken);
            }
            catch (NoSuchElementException ex) {
                result.setShouldSetCookie(true);
            }
            if (requestSession != null) {
                if (System.currentTimeMillis() < requestSession.getExpiration()) {
                    return result;
                }
                else {
                    result.setShouldSetCookie(true);
                    sessionRepository.deleteByToken(sessionToken);
                }
            }
        }
        if (loginCredentials == null) {
            result.setException(new IllegalArgumentException());
            return result;
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readByName(loginCredentials.getName());
        }
        catch (NoSuchElementException ex) {
            result.setException(new AccessDeniedException());
            return result;
        }
        catch (RuntimeException ex) {
            result.setException(ex);
            return result;
        }
        String passwordHash = secureHashService.hash(loginCredentials.getPassword());
        if (!userAccount.getPasswordHash().equals(passwordHash)) {
            result.setException(new AccessDeniedException());
            return result;
        }
        String token = generateToken();
        Session session = new Session(token, userAccount.getId(), System.currentTimeMillis() + sessionMaxDuration * 1000L);
        sessionRepository.set(session);
        return new AuthenticationResult(true, token, sessionMaxDuration - 1, null);
    }

    @Override
    public AuthenticationResult logout(String sessionToken) {
        try {
            sessionRepository.deleteByToken(sessionToken);
        }
        catch (IllegalArgumentException ex) {
            return new AuthenticationResult(false, null, 0, null);
        }
        return new AuthenticationResult(true, null, 0, null);
    }

    private String generateToken() {
        String token = UUID.randomUUID().toString();
        while (true) {
            try {
                sessionRepository.readByToken(token);
            }
            catch (NoSuchElementException ex) {
                break;
            }
            token = UUID.randomUUID().toString();
        }
        return token;
    }
}
