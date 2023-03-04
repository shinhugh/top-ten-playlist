package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.SongListEntry;
import org.springframework.data.repository.CrudRepository;

public interface SongListEntryCrudRepository extends CrudRepository<SongListEntry, String> { }
