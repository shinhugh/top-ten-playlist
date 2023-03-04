package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListContainer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SongListContainerCrudRepository extends CrudRepository<SongListContainer, String> {
    Optional<SongListContainer> findByUserProfileId(String userProfileId);
}
