package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionCrudRepository extends CrudRepository<Session, String> {
    void deleteByUserAccountId(String userAccountId);
    void deleteByExpirationLessThanEqual(long expiration);
}
