package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.service.SongListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/song-list")
public class SongListController {
    private SongListService songListService;

    public SongListController(SongListService songListService) {
        this.songListService = songListService;
    }

    // TODO
}
