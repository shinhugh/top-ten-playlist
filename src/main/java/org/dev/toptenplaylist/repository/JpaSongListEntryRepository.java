package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSongListEntryRepository implements SongListEntryRepository {
    private final SongListEntryCrudRepository songListEntryCrudRepository;

    public JpaSongListEntryRepository(SongListEntryCrudRepository songListEntryCrudRepository) {
        this.songListEntryCrudRepository = songListEntryCrudRepository;
    }

    // TODO

    @Override
    public String set(SongListEntry songListEntry) {
        return null;
    }

    // TODO
}
