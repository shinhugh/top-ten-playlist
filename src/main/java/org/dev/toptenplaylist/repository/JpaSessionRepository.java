package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
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
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String set(Session session) {
        try {
            sessionCrudRepository.save(session);
        }
        catch (java.lang.IllegalArgumentException | JpaSystemException ex) {
            throw new IllegalArgumentException();
        }
        catch (DataIntegrityViolationException ex) {
            System.out.println("@@ DEBUG: " + ex.getClass() + " | " + ex.getMessage()); // DEBUG
//            if () { // TODO: If non-null rule is violated
//                throw new IllegalArgumentException();
//            }
//            else { // TODO: If unique rule is violated
//                throw new ElementAlreadyExistsException();
//            }
            throw new ElementAlreadyExistsException();
        }
        return session.getToken();
    }

    @Override
    public void deleteByToken(String token) {
        try {
            sessionCrudRepository.deleteById(token);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteByUserAccountId(String userAccountId) {
        try {
            sessionCrudRepository.deleteByUserAccountId(userAccountId);
        }
        catch (java.lang.IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(long expiration) {
        sessionCrudRepository.deleteByExpirationLessThanEqual(expiration);
    }
}
