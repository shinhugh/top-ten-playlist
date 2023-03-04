package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;

import java.util.List;

public interface SongListEntryRepository {
    List<SongListEntry> readBySongListContainerId(String songListContainerId);
    String set(SongListEntry songListEntry);
    void deleteBySongListContainerId(String songListContainerId);
}
