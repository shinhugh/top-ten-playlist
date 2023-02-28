package org.dev.toptenplaylist.model;

import java.util.UUID;

public class SongListEntry implements Identifiable {
    private UUID id;
    private UUID songListId;
    private int rank;
    private String title;
    private String artist;
    private String contentUrl;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSongListId() {
        return songListId;
    }

    public void setSongListId(UUID songListId) {
        this.songListId = songListId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
