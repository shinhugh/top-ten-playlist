package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public class InMemorySessionRepository implements SessionRepository {
    @Override
    public Session readByToken(String token) {
        // TODO
        return null;
    }

    @Override
    public String set(Session session) {
        // TODO
        return null;
    }

    @Override
    public void deleteByToken(String token) {
        // TODO
    }

    @Override
    public void deleteByUserAccountId(UUID userAccountId) {
        // TODO
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(Date expiration) {
        // TODO
    }
}
