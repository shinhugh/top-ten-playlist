package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaSessionRepository implements SessionRepository {
    private final SessionCrudRepository jpaSessionRepository;

    public JpaSessionRepository(SessionCrudRepository jpaSessionRepository) {
        this.jpaSessionRepository = jpaSessionRepository;
    }

    @Override
    public Session readByToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return jpaSessionRepository.findById(token).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void set(Session session) {
        if (session == null) {
            throw new IllegalArgumentException();
        }
        jpaSessionRepository.save(session);
    }

    @Override
    public void deleteByToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        if (!jpaSessionRepository.existsById(token)) {
            throw new NoSuchElementException();
        }
        jpaSessionRepository.deleteById(token);
    }

    @Override
    public void deleteByUserAccountId(String userAccountId) {
        if (userAccountId == null) {
            throw new IllegalArgumentException();
        }
        List<Session> sessions = jpaSessionRepository.findByUserAccountId(userAccountId);
        for (Session session : sessions) {
            jpaSessionRepository.delete(session);
        }
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(long expiration) {
        List<Session> sessions = jpaSessionRepository.findByExpirationLessThanEqual(expiration);
        for (Session session : sessions) {
            jpaSessionRepository.delete(session);
        }
    }
}
