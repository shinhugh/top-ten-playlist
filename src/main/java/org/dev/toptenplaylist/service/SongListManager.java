package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.SongList;
import org.dev.toptenplaylist.model.SongListContainer;
import org.dev.toptenplaylist.model.SongListEntry;
import org.dev.toptenplaylist.model.UserProfile;
import org.dev.toptenplaylist.repository.SongListContainerRepository;
import org.dev.toptenplaylist.repository.SongListEntryRepository;
import org.dev.toptenplaylist.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    public void create(String activeUserAccountId, SongList songList) {
        // TODO: Populate title and artist fields using provided content URL
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        try {
            songListContainerRepository.readByUserProfileId(userProfile.getId());
            throw new ElementAlreadyExistsException();
        }
        catch (NoSuchElementException ignored) { }
        if (songList == null) {
            throw new IllegalArgumentException();
        }
        SongList.Entry[] songListEntries = songList.getEntries();
        if (songListEntries == null || songListEntries.length > 10) {
            throw new IllegalArgumentException();
        }
        for (SongList.Entry entry : songListEntries) {
            if (entry == null || entry.getContentUrl() == null) {
                throw new IllegalArgumentException();
            }
            entry.setContentUrl(transformContentUrl(entry.getContentUrl()));
        }
        SongListContainer songListContainer = new SongListContainer(userProfile.getId(), songList.getTitle(), System.currentTimeMillis());
        String songListContainerId = songListContainerRepository.set(songListContainer);
        for (int i = 0; i < songListEntries.length; i++) {
            SongListEntry songListEntry = new SongListEntry(songListContainerId, i, songListEntries[i].getTitle(), songListEntries[i].getArtist(), songListEntries[i].getContentUrl());
            songListEntryRepository.set(songListEntry);
        }
    }

    @Override
    public void updateById(String activeUserAccountId, String id, SongList songList) {
        // TODO: Populate title and artist fields using provided content URL
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
        if (songListEntries == null || songListEntries.length > 10) {
            throw new IllegalArgumentException();
        }
        for (SongList.Entry entry : songListEntries) {
            if (entry == null || entry.getContentUrl() == null) {
                throw new IllegalArgumentException();
            }
            entry.setContentUrl(transformContentUrl(entry.getContentUrl()));
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
        // TODO: Populate title and artist fields using provided content URL
        if (activeUserAccountId == null) {
            throw new AccessDeniedException();
        }
        UserProfile userProfile = userProfileRepository.readByUserAccountId(activeUserAccountId);
        SongListContainer songListContainer = songListContainerRepository.readByUserProfileId(userProfile.getId());
        if (songList == null) {
            throw new IllegalArgumentException();
        }
        SongList.Entry[] songListEntries = songList.getEntries();
        if (songListEntries == null || songListEntries.length > 10) {
            throw new IllegalArgumentException();
        }
        for (SongList.Entry entry : songListEntries) {
            if (entry == null || entry.getContentUrl() == null) {
                throw new IllegalArgumentException();
            }
            entry.setContentUrl(transformContentUrl(entry.getContentUrl()));
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

    private String transformContentUrl(String contentUrl) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(contentUrl).build();
        String host = uriComponents.getHost();
        String path = uriComponents.getPath();
        List<String> pathSegments = uriComponents.getPathSegments();
        MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
        if ("youtube-nocookie.com".equals(host) || "www.youtube-nocookie.com".equals(host)) {
            if (pathSegments.size() == 2 && "embed".equals(pathSegments.get(0))) {
                return "https://www.youtube-nocookie.com/embed/" + pathSegments.get(1);
            }
            throw new IllegalArgumentException();
        }
        if ("youtube.com".equals(host) || "www.youtube.com".equals(host)) {
            if (pathSegments.size() == 2 && "embed".equals(pathSegments.get(0))) {
                return "https://www.youtube-nocookie.com/embed/" + pathSegments.get(1);
            }
            String youtubeId = queryParams.getFirst("v");
            if (youtubeId == null) {
                throw new IllegalArgumentException();
            }
            return "https://www.youtube-nocookie.com/embed/" + youtubeId;
        }
        if ("music.youtube.com".equals(host)) {
            String youtubeId = queryParams.getFirst("v");
            if (youtubeId == null) {
                throw new IllegalArgumentException();
            }
            return "https://www.youtube-nocookie.com/embed/" + youtubeId;
        }
        if ("w.soundcloud.com".equals(host)) {
            String soundcloudUrl = queryParams.getFirst("url");
            if (soundcloudUrl == null) {
                throw new IllegalArgumentException();
            }
            UriComponents soundcloudUriComponents = UriComponentsBuilder.fromUriString(soundcloudUrl).build();
            String soundcloudHost = soundcloudUriComponents.getHost();
            String soundcloudPath = soundcloudUriComponents.getPath();
            if (!("soundcloud.com".equals(soundcloudHost) || "www.soundcloud.com".equals(soundcloudHost)) || soundcloudPath == null) {
                throw new IllegalArgumentException();
            }
            return "https://w.soundcloud.com/player/?url=https://soundcloud.com" + soundcloudPath + "&visual=true&show_comments=false&show_teaser=false&hide_related=true";
        }
        if ("soundcloud.com".equals(host) || "www.soundcloud.com".equals(host)) {
            if (path == null) {
                throw new IllegalArgumentException();
            }
            return "https://w.soundcloud.com/player/?url=https://soundcloud.com" + path + "&visual=true&show_comments=false&show_teaser=false&hide_related=true";
        }
        throw new IllegalArgumentException();
    }
}
