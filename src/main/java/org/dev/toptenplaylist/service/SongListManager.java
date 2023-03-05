package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.model.SongList;
import org.dev.toptenplaylist.model.SongListContainer;
import org.dev.toptenplaylist.model.SongListEntry;
import org.dev.toptenplaylist.model.UserProfile;
import org.dev.toptenplaylist.repository.SongListContainerRepository;
import org.dev.toptenplaylist.repository.SongListEntryRepository;
import org.dev.toptenplaylist.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListManager implements SongListService {
    private final SongListContainerRepository songListContainerRepository;
    private final SongListEntryRepository songListEntryRepository;
    private final UserProfileRepository userProfileRepository;

    public SongListManager(SongListContainerRepository songListContainerRepository, SongListEntryRepository songListEntryRepository, UserProfileRepository userProfileRepository) {
        this.songListContainerRepository = songListContainerRepository;
        this.songListEntryRepository = songListEntryRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public SongList readById(String id) {
        SongListContainer songListContainer = songListContainerRepository.readById(id);
        List<SongListEntry> songListEntries = songListEntryRepository.readBySongListContainerId(id);
        return new SongList(songListContainer, songListEntries);
    }

    @Override
    public SongList readByActiveUserAccountId(String activeUserAccountId) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(userProfile.getId());
        List<SongListEntry> songListEntries = songListEntryRepository.readBySongListContainerId(songListContainer.getId());
        return new SongList(songListContainer, songListEntries);
    }

    @Override
    public SongList readByUserPublicName(String userPublicName) {
        UserProfile userProfile = userProfileRepository.readByName(userPublicName);
        SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(userProfile.getId());
        List<SongListEntry> songListEntries = songListEntryRepository.readBySongListContainerId(songListContainer.getId());
        return new SongList(songListContainer, songListEntries);
    }

    @Override
    public void create(String activeUserAccountId, SongList songList) { // TODO: Populate title and artist fields using provided content URL
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        if (songList == null) {
            throw new IllegalArgumentException();
        }
        SongList.Entry[] songListEntries = songList.getEntries();
        if (songListEntries == null) {
            throw new IllegalArgumentException();
        }
        for (SongList.Entry entry : songListEntries) {
            if (entry == null || entry.getContentUrl() == null) {
                throw new IllegalArgumentException();
            }
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        SongListContainer songListContainer = new SongListContainer(userProfile.getId(), songList.getTitle(), System.currentTimeMillis());
        String songListContainerId = songListContainerRepository.set(songListContainer);
        for (int i = 0; i < songListEntries.length; i++) {
            SongListEntry songListEntry = new SongListEntry(songListContainerId, i, songListEntries[i].getTitle(), songListEntries[i].getArtist(), songListEntries[i].getContentUrl());
            songListEntryRepository.set(songListEntry);
        }
    }

    @Override
    public void updateById(String activeUserAccountId, String id, SongList songList) {
        SongListContainer songListContainer = songListContainerRepository.readById(id);
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        if (!songListContainer.getUserProfileId().equals(userProfile.getId())) {
            throw new AccessDeniedException();
        }
        if (songList == null) {
            throw new IllegalArgumentException();
        }
        SongList.Entry[] songListEntries = songList.getEntries();
        if (songListEntries == null) {
            throw new IllegalArgumentException();
        }
        for (SongList.Entry entry : songListEntries) {
            if (entry == null || entry.getContentUrl() == null) {
                throw new IllegalArgumentException();
            }
        }
        songListContainer.setTitle(songList.getTitle());
        songListContainer.setLastModificationDate(System.currentTimeMillis());
        songListContainerRepository.set(songListContainer);
        songListEntryRepository.deleteBySongListContainerId(id);
        String songListContainerId = songListContainerRepository.set(songListContainer);
        for (int i = 0; i < songListEntries.length; i++) {
            SongListEntry songListEntry = new SongListEntry(songListContainerId, i, songListEntries[i].getTitle(), songListEntries[i].getArtist(), songListEntries[i].getContentUrl());
            songListEntryRepository.set(songListEntry);
        }
    }

    @Override
    public void updateByActiveUserAccountId(String activeUserAccountId, SongList songList) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(userProfile.getId());
        if (songList == null) {
            throw new IllegalArgumentException();
        }
        SongList.Entry[] songListEntries = songList.getEntries();
        if (songListEntries == null) {
            throw new IllegalArgumentException();
        }
        for (SongList.Entry entry : songListEntries) {
            if (entry == null || entry.getContentUrl() == null) {
                throw new IllegalArgumentException();
            }
        }
        songListContainer.setTitle(songList.getTitle());
        songListContainer.setLastModificationDate(System.currentTimeMillis());
        songListContainerRepository.set(songListContainer);
        songListEntryRepository.deleteBySongListContainerId(songListContainer.getId());
        String songListContainerId = songListContainerRepository.set(songListContainer);
        for (int i = 0; i < songListEntries.length; i++) {
            SongListEntry songListEntry = new SongListEntry(songListContainerId, i, songListEntries[i].getTitle(), songListEntries[i].getArtist(), songListEntries[i].getContentUrl());
            songListEntryRepository.set(songListEntry);
        }
    }

    @Override
    public void deleteById(String activeUserAccountId, String id) {
        SongListContainer songListContainer = songListContainerRepository.readById(id);
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        if (!songListContainer.getUserProfileId().equals(userProfile.getId())) {
            throw new AccessDeniedException();
        }
        songListContainerRepository.deleteById(id);
        songListEntryRepository.deleteBySongListContainerId(id);
    }

    @Override
    public void deleteByActiveUserAccountId(String activeUserAccountId) {
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(userProfile.getId());
        songListContainerRepository.deleteById(songListContainer.getId());
        songListEntryRepository.deleteBySongListContainerId(songListContainer.getId());
    }
}
