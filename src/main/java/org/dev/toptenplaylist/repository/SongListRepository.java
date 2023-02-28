package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongList;

import java.util.UUID;

public interface SongListRepository {
    SongList readByUserProfileId(UUID userProfileId);
    UUID set(SongList songList);
    void deleteByUserProfileId(UUID userProfileId);
}
