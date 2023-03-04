package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListContainer;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSongListContainerRepository implements SongListContainerRepository {
    private final SongListContainerCrudRepository songListContainerCrudRepository;

    public JpaSongListContainerRepository(SongListContainerCrudRepository songListContainerCrudRepository) {
        this.songListContainerCrudRepository = songListContainerCrudRepository;
    }

    @Override
    public SongListContainer readById(String id) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public SongListContainer readByUserProfileId(String userProfileId) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public String set(SongListContainer songListContainer) {
        // TODO
        throw new RuntimeException();
    }

    @Override
    public void deleteById(String id) {
        // TODO
        throw new RuntimeException();
    }
}
