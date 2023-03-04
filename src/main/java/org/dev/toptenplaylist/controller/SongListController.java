package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.SongList;
import org.dev.toptenplaylist.service.SessionTokenService;
import org.dev.toptenplaylist.service.SongListService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/song-list")
public class SongListController {
    private final SessionTokenService sessionTokenService;
    private final SongListService songListService;

    public SongListController(SessionTokenService sessionTokenService, SongListService songListService) {
        this.sessionTokenService = sessionTokenService;
        this.songListService = songListService;
    }

    @PostMapping
    public void create(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody(required = false) SongList songList) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        songListService.create(activeUserAccountId, songList);
    }

    @GetMapping("/id/{id}")
    public SongList readById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        sessionTokenService.handleSessionToken(response, sessionToken);
        return songListService.readById(id);
    }

    @GetMapping("/session")
    public SongList readBySession(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        return songListService.readByActiveUserAccountId(activeUserAccountId);
    }

    @GetMapping("/user-public-name/{userPublicName}")
    public SongList readByUserPublicName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String userPublicName) {
        sessionTokenService.handleSessionToken(response, sessionToken);
        return songListService.readByUserPublicName(userPublicName);
    }

    @PutMapping("/id/{id}")
    public void updateById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id, @RequestBody(required = false) SongList songList) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        songListService.updateById(activeUserAccountId, id, songList);
    }

    @PutMapping("/session")
    public void updateBySession(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody(required = false) SongList songList) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        songListService.updateByActiveUserAccountId(activeUserAccountId, songList);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        songListService.deleteById(activeUserAccountId, id);
    }

    @DeleteMapping("/session")
    public void deleteBySession(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        songListService.deleteByActiveUserAccountId(activeUserAccountId);
    }
}
