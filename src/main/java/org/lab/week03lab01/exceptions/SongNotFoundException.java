package org.lab.week03lab01.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SongNotFoundException extends Exception {
    private final Long songId;

    public SongNotFoundException(Long songId) {
        super("Song with id " + songId + " not found");
        this.songId = songId;
    }

    public Long getSongId() {
        return songId;
    }
}

//
//public class SongNotFoundException extends NotFoundException {
//    private final Long songId;
//
//    public SongNotFoundException(Long songId) {
//        super("Song with id " + songId + " not found", "Song");
//        this.songId = songId;
//    }
//
//    public Long getSongId() {
//        return songId;
//    }
//}
