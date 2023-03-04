package org.dev.toptenplaylist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SongListEntry implements Identifiable {
    @Id
    private String id;
    @Column(nullable = false)
    private String songListContainerId;
    @Column(nullable = false)
    private int rank;
    private String title;
    private String artist;
    @Column(nullable = false)
    private String contentUrl;

    public SongListEntry() { }

    public SongListEntry(String songListContainerId, int rank, String title, String artist, String contentUrl) {
        this.songListContainerId = songListContainerId;
        this.rank = rank;
        this.title = title;
        this.artist = artist;
        this.contentUrl = contentUrl;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSongListContainerId() {
        return songListContainerId;
    }

    public void setSongListContainerId(String songListContainerId) {
        this.songListContainerId = songListContainerId;
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
