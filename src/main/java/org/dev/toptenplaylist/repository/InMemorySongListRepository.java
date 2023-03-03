package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongList;
import org.springframework.stereotype.Repository;

@Repository
public class InMemorySongListRepository implements SongListRepository {
    @Override
    public String set(SongList songList) {
        // TODO
        throw new RuntimeException();
    }
}
