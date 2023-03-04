package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaSongListEntryRepository implements SongListEntryRepository {
    private final SongListEntryCrudRepository songListEntryCrudRepository;

    public JpaSongListEntryRepository(SongListEntryCrudRepository songListEntryCrudRepository) {
        this.songListEntryCrudRepository = songListEntryCrudRepository;
    }

    @Override
    public List<SongListEntry> readBySongListContainerId(String songListContainerId) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public String set(SongListEntry songListEntry) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void deleteBySongListContainerId(String songListContainerId) {
        // TODO
        throw new RuntimeException();
    }
}
