package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListContainer;

public interface SongListContainerRepository {
    SongListContainer readById(String id);
    SongListContainer readByUserProfileId(String userProfileId);
    String set(SongListContainer songListContainer);
    void deleteById(String id);
}
