package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.SongList;

public interface SongListService {
    public SongList readById(String activeUserAccountId, String id);
    public SongList readByActiveUserAccountId(String activeUserAccountId);
    public SongList readByUserPublicName(String userPublicName);
    public void create(String activeUserAccountId, SongList songList);
    public void updateById(String activeUserAccountId, String id, SongList songList);
    public void updateByActiveUserAccountId(String activeUserAccountId, SongList songList);
    public void deleteById(String activeUserAccountId, String id);
    public void deleteByActiveUserAccountId(String activeUserAccountId);
}
