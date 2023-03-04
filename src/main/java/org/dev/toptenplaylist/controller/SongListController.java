package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.service.SessionTokenService;
import org.dev.toptenplaylist.service.SongListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/song-list")
public class SongListController {
    private final SessionTokenService sessionTokenService;
    private final SongListService songListService;

    public SongListController(SessionTokenService sessionTokenService, SongListService songListService) {
        this.sessionTokenService = sessionTokenService;
        this.songListService = songListService;
    }

    // TODO
}
