package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.SongList;
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

    @Override
    public SongList readById(String activeUserAccountId, String id) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public SongList readByActiveUserAccountId(String activeUserAccountId) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public SongList readByUserPublicName(String userPublicName) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void create(String activeUserAccountId, SongList songList) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void updateById(String activeUserAccountId, String id, SongList songList) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void updateByActiveUserAccountId(String activeUserAccountId, SongList songList) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void deleteById(String activeUserAccountId, String id) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void deleteByActiveUserAccountId(String activeUserAccountId) {
        // TODO
        throw new RuntimeException();
    }
}
