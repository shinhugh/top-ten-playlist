package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;

import java.util.UUID;

public interface SongListEntryRepository {
    SongListEntry readBySongListId(UUID songListId);
    UUID set(SongListEntry songListEntry);
    void deleteBySongListId(UUID songListId);
    void deleteBySongListIdAndRank(UUID songListId, int rank);
}
