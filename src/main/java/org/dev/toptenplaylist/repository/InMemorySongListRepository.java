package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongList;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InMemorySongListRepository implements SongListRepository {
    @Override
    public UUID set(SongList songList) {
        // TODO
        throw new RuntimeException();
    }
}
