package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;

import java.util.Date;
import java.util.UUID;

public interface SessionRepository {
    Session readByToken(String token);
    void set(Session session);
    void deleteByToken(String token);
    void deleteByUserAccountId(UUID  userAccountId);
    void deleteByLessThanOrEqualToExpiration(Date expiration);
}
