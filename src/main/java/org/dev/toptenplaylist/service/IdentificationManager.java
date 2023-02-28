package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.repository.SessionRepository;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IdentificationManager implements IdentificationService {
    private final SessionRepository sessionRepository;
    private final UserAccountRepository userAccountRepository;

    public IdentificationManager(SessionRepository sessionRepository, UserAccountRepository userAccountRepository) {
        this.sessionRepository =  sessionRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount identifyCurrentUser(String sessionToken) {
        if (sessionToken == null) {
            throw new AccessDeniedException();
        }
        Session session;
        try {
            session = sessionRepository.readByToken(sessionToken);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException();
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException();
        }
        Date currentTime = new Date();
        if (session.getExpiration().compareTo(currentTime) <= 0) {
            try {
                sessionRepository.deleteByToken(sessionToken);
            }
            catch (NoSuchElementException | IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            throw new AccessDeniedException();
        }
        try {
            return userAccountRepository.readById(session.getUserAccountId());
        }
        catch  (NoSuchElementException | IllegalArgumentException ex) {
            throw new RuntimeException();
        }
    }
}
