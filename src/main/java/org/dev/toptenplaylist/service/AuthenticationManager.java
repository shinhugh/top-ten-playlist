package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.dev.toptenplaylist.model.SessionCookie;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.model.UserCredentials;
import org.dev.toptenplaylist.repository.SessionRepository;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationManager implements AuthenticationService {
    private final SessionRepository sessionRepository;
    private final UserAccountRepository userAccountRepository;
    private final IdentificationService identificationService;
    private final SecureHashService secureHashService;
    private final int sessionMaxDuration = 7200;

    public AuthenticationManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository, IdentificationService identificationService, SecureHashService secureHashService) {
        this.sessionRepository = sessionRepository;
        this.userAccountRepository =  userAccountRepository;
        this.identificationService = identificationService;
        this.secureHashService = secureHashService;
    }

    @Override
    public SessionCookie login(String sessionToken, UserCredentials userCredentials) {
        try {
            identificationService.identifyCurrentUser(sessionToken);
            Session existingSession;
            try {
                existingSession = sessionRepository.readByToken(sessionToken);
            }
            catch (NoSuchElementException ex) {
                throw new RuntimeException();
            }
            int maxAge = (int) ((existingSession.getExpiration().getTime() - System.currentTimeMillis()) / 1000);
            return new SessionCookie(sessionToken, maxAge);
        }
        catch (AccessDeniedException ignored) { }
        if (userCredentials == null || userCredentials.getName() == null || userCredentials.getPassword() == null) {
            throw new IllegalArgumentException();
        }
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readByName(userCredentials.getName());
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        String passwordHash = secureHashService.hash(userCredentials.getPassword());
        if (!userAccount.getPasswordHash().equals(passwordHash)) {
            throw new AccessDeniedException();
        }
        String token = generateToken();
        Session session = new Session();
        session.setToken(token);
        session.setUserAccountId(userAccount.getId());
        session.setExpiration((new Date(System.currentTimeMillis() + sessionMaxDuration * 1000)));
        try {
            sessionRepository.set(session);
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        return new SessionCookie(token, sessionMaxDuration - 1);
    }

    @Override
    public void logout(String sessionToken) {
        if (sessionToken == null) {
            return;
        }
        try {
            sessionRepository.deleteByToken(sessionToken);
        }
        catch (NoSuchElementException ignored) { }
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
