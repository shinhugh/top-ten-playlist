package org.dev.toptenplaylist.model;

import java.util.UUID;

public class SongList implements Identifiable {
    private UUID id;
    private UUID userProfileId;
    private String title;
    private long lastModificationDate;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UUID userProfileId) {
        this.userProfileId = userProfileId;
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
}
