package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionCrudRepository extends CrudRepository<Session, String> {
    List<Session> findByUserAccountId(String userAccountId);
    List<Session> findByExpirationLessThanEqual(long expiration);
}
