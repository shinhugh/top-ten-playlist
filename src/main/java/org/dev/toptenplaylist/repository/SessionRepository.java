package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;

public interface SessionRepository {
    Session readByToken(String token);
    String set(Session session);
    void deleteByToken(String token);
    void deleteByUserAccountId(String userAccountId);
    void deleteByLessThanOrEqualToExpiration(long expiration);
}
