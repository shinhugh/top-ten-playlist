package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongList;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InMemorySongListRepository implements SongListRepository {
    @Override
    public SongList readByUserProfileId(UUID userProfileId) {
        // TODO
        return null;
    }

    @Override
    public UUID set(SongList songList) {
        // TODO
        return null;
    }

    @Override
    public void deleteByUserProfileId(UUID userProfileId) {
        // TODO
    }
}
