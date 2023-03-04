package org.dev.toptenplaylist.model;

public class SongList {
    private String id;
    private String userId;
    private String title;
    private long lastModificationDate;
    private Entry[] entries;

    public SongList() { }

    public SongList(SongListContainer songListContainer, SongListEntry[] songListEntries) {
        id = songListContainer.getId();
        userId = songListContainer.getUserProfileId();
        title = songListContainer.getTitle();
        lastModificationDate = songListContainer.getLastModificationDate();
        entries = new Entry[songListEntries.length];
        for (int i = 0; i < songListEntries.length; i++) {
            entries[i] = new Entry(songListEntries[i]);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(long lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Entry[] getEntries() {
        return entries;
    }

    public void setEntries(Entry[] entries) {
        this.entries = new Entry[entries.length];
        System.arraycopy(entries, 0, this.entries, 0, entries.length);
    }

    public static class Entry {
        private String title;
        private String artist;
        private String contentUrl;

        public Entry() { }

        public Entry(SongListEntry songListEntry) {
            title = songListEntry.getTitle();
            artist = songListEntry.getArtist();
            contentUrl = songListEntry.getContentUrl();
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
}
