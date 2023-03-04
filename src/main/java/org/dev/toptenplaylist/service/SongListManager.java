package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.repository.SongListContainerRepository;
import org.dev.toptenplaylist.repository.SongListEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class SongListManager implements SongListService {
    private final SongListContainerRepository songListContainerRepository;
    private final SongListEntryRepository songListEntryRepository;

    public SongListManager(SongListContainerRepository songListContainerRepository, SongListEntryRepository songListEntryRepository) {
        this.songListContainerRepository = songListContainerRepository;
        this.songListEntryRepository = songListEntryRepository;
    }

    // TODO
}
