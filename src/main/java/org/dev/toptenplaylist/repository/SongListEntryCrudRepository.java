package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SongListEntryCrudRepository extends CrudRepository<SongListEntry, String> {
    List<SongListEntry> findBySongListContainerId(String songListContainerId);
    @Transactional
    void deleteBySongListContainerId(String songListContainerId);
}
