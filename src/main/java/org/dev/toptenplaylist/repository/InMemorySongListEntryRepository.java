package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InMemorySongListEntryRepository implements SongListEntryRepository {
    @Override
    public UUID set(SongListEntry songListEntry) {
        // TODO
        throw new RuntimeException();
    }
}
