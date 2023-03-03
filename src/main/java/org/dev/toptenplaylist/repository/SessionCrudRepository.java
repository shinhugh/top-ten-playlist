package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface SessionCrudRepository extends CrudRepository<Session, String> {
    @Transactional
    void deleteByUserAccountId(String userAccountId);
    @Transactional
    void deleteByExpirationLessThanEqual(long expiration);
}
