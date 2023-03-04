package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.ElementAlreadyExistsException;
import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.SongListContainer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaSongListContainerRepository implements SongListContainerRepository {
    private final SongListContainerCrudRepository songListContainerCrudRepository;

    public JpaSongListContainerRepository(SongListContainerCrudRepository songListContainerCrudRepository) {
        this.songListContainerCrudRepository = songListContainerCrudRepository;
    }

    @Override
    public SongListContainer readById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return songListContainerCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public SongListContainer readByUserProfileId(String userProfileId) {
        if (userProfileId == null) {
            throw new IllegalArgumentException();
        }
        return songListContainerCrudRepository.findByUserProfileId(userProfileId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String set(SongListContainer songListContainer) {
        if (songListContainer == null || songListContainer.getUserProfileId() == null) {
            throw new IllegalArgumentException();
        }
        String id = songListContainer.getId();
        if (id == null) {
            id = generateId();
            songListContainer.setId(id);
        }
        else {
            try {
                UUID.fromString(id);
            }
            catch (java.lang.IllegalArgumentException ex) {
                throw new IllegalArgumentException();
            }
        }
        try {
            songListContainerCrudRepository.save(songListContainer);
        }
        catch (DataIntegrityViolationException ex) {
            throw new ElementAlreadyExistsException();
        }
        return id;
    }

    @Override
    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        try {
            songListContainerCrudRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ignored) { }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        while (songListContainerCrudRepository.existsById(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
