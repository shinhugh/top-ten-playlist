package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSessionRepository implements SessionRepository {
    private final SessionCrudRepository sessionCrudRepository;

    public JpaSessionRepository(SessionCrudRepository sessionCrudRepository) {
        this.sessionCrudRepository = sessionCrudRepository;
    }

    @Override
    public Session readByToken(String token) {
        try {
            return sessionCrudRepository.findById(token).orElseThrow(NoSuchElementException::new);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String set(Session session) {
        if (session == null || session.getToken() == null || session.getUserAccountId() == null) {
            throw new IllegalArgumentException();
        }
        sessionCrudRepository.save(session);
        return session.getToken();
    }

    @Override
    public void deleteByToken(String token) {
        try {
            sessionCrudRepository.deleteById(token);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteByUserAccountId(String userAccountId) {
        try {
            sessionCrudRepository.deleteByUserAccountId(userAccountId);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(long expiration) {
        sessionCrudRepository.deleteByExpirationLessThanEqual(expiration);
    }
}
