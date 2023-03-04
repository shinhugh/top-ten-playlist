package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaSongListEntryRepository implements SongListEntryRepository {
    private final SongListEntryCrudRepository songListEntryCrudRepository;

    public JpaSongListEntryRepository(SongListEntryCrudRepository songListEntryCrudRepository) {
        this.songListEntryCrudRepository = songListEntryCrudRepository;
    }

    @Override
    public List<SongListEntry> readBySongListContainerId(String songListContainerId) {
        if (songListContainerId == null) {
            throw new IllegalArgumentException();
        }
        return songListEntryCrudRepository.findBySongListContainerId(songListContainerId);
    }

    @Override
    public String set(SongListEntry songListEntry) {
        if (songListEntry == null || songListEntry.getSongListContainerId() == null || songListEntry.getContentUrl() == null) {
            throw new IllegalArgumentException();
        }
        String id = songListEntry.getId();
        if (id == null) {
            id = generateId();
            songListEntry.setId(id);
        }
        else {
            try {
                UUID.fromString(id);
            }
            catch (java.lang.IllegalArgumentException ex) {
                throw new IllegalArgumentException();
            }
        }
        songListEntryCrudRepository.save(songListEntry);
        return id;
    }

    @Override
    public void deleteBySongListContainerId(String songListContainerId) {
        if (songListContainerId == null) {
            throw new IllegalArgumentException();
        }
        songListEntryCrudRepository.deleteBySongListContainerId(songListContainerId);
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        while (songListEntryCrudRepository.existsById(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
