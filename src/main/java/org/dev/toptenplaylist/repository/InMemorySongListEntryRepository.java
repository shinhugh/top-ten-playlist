package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.stereotype.Repository;

@Repository
public class InMemorySongListEntryRepository implements SongListEntryRepository {
    @Override
    public String set(SongListEntry songListEntry) {
        // TODO
        throw new RuntimeException();
    }
}
