package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountCrudRepository extends CrudRepository<UserAccount, String> {
    Optional<UserAccount> findByName(String name);
}
