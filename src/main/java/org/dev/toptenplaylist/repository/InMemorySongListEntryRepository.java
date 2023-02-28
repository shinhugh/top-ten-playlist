package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InMemorySongListEntryRepository implements SongListEntryRepository {
    @Override
    public SongListEntry readBySongListId(UUID songListId) {
        // TODO
        return null;
    }

    @Override
    public UUID set(SongListEntry songListEntry) {
        // TODO
        return null;
    }

    @Override
    public void deleteBySongListId(UUID songListId) {
        // TODO
    }

    @Override
    public void deleteBySongListIdAndRank(UUID songListId, int rank) {
        // TODO
    }
}
