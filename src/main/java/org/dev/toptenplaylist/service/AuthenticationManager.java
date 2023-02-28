package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.IllegalArgumentException;
import org.dev.toptenplaylist.model.*;
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

    public AuthenticationManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository, IdentificationService identificationService, SecureHashService secureHashService) {
        this.sessionRepository = sessionRepository;
        this.userAccountRepository =  userAccountRepository;
        this.identificationService = identificationService;
        this.secureHashService = secureHashService;
    }

    @Override
    public String login(UserCredentials userCredentials) {
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
        session.setExpiration((new Date(System.currentTimeMillis() + 60000))); // TODO: Specify value elsewhere
        try {
            sessionRepository.set(session);
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        return token;
    }

    @Override
    public void logout(String sessionToken) {
        try {
            identificationService.identifyCurrentUser(sessionToken);
        }
        catch (AccessDeniedException ex) {
            throw new NoSuchElementException();
        }
        try {
            sessionRepository.deleteByToken(sessionToken);
        }
        catch (NoSuchElementException ex) {
            throw new RuntimeException();
        }
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
