package org.dev.toptenplaylist.model;

import java.util.UUID;

public interface Identifiable {
    UUID getId();
    void setId(UUID id);
}
