package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSessionRepository implements SessionRepository {
    private final SessionCrudRepository sessionCrudRepository;

    public JpaSessionRepository(SessionCrudRepository sessionCrudRepository) {
        this.sessionCrudRepository = sessionCrudRepository;
    }

    @Override
    public Session readByToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return sessionCrudRepository.findById(token).orElseThrow(NoSuchElementException::new);
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
        if (token == null) {
            throw new IllegalArgumentException();
        }
        try {
            sessionCrudRepository.deleteById(token);
        }
        catch (EmptyResultDataAccessException ignored) { }
    }

    @Override
    public void deleteByUserAccountId(String userAccountId) {
        if (userAccountId == null) {
            throw new IllegalArgumentException();
        }
        sessionCrudRepository.deleteByUserAccountId(userAccountId);
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(long expiration) {
        sessionCrudRepository.deleteByExpirationLessThanEqual(expiration);
    }
}
