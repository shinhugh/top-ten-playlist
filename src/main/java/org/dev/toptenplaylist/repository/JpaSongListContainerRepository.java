package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListContainer;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSongListContainerRepository implements SongListContainerRepository {
    private final SongListContainerCrudRepository songListContainerCrudRepository;

    public JpaSongListContainerRepository(SongListContainerCrudRepository songListContainerCrudRepository) {
        this.songListContainerCrudRepository = songListContainerCrudRepository;
    }

    // TODO

    @Override
    public String set(SongListContainer songListContainer) {
        return null;
    }

    // TODO
}
