package org.lab.week03lab01.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AlbumNotFoundException extends Exception {
    private final Long albumId;

    public AlbumNotFoundException(Long albumId) {
        super("Album with id " + albumId + " not found");
        this.albumId = albumId;
    }

    public Long getAlbumId() {
        return albumId;
    }
}
