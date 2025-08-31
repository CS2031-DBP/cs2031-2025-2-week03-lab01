package org.lab.week03lab01.controller;

import jakarta.validation.Valid;
import org.lab.week03lab01.exceptions.AlbumNotFoundException;
import org.lab.week03lab01.exceptions.SongNotFoundException;
import org.lab.week03lab01.model.Album;
import org.lab.week03lab01.model.CreateAlbumDTO;
import org.lab.week03lab01.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.findAll();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) throws AlbumNotFoundException {
        Album album = albumService.findById(id);
        return ResponseEntity.ok(album);
    }

    @PostMapping
    public ResponseEntity<Album> createAlbum(@Valid @RequestBody CreateAlbumDTO createAlbumDTO) {
        var createdAlbum = albumService.createAlbum(createAlbumDTO);
        URI location = URI.create("albums/" + createdAlbum.getId());
        return ResponseEntity.created(location).body(createdAlbum);
    }

    @PatchMapping("/{id}/add-song/{song_id}")
    public ResponseEntity<Album> addSong(@PathVariable("id") Long albumId, @PathVariable("song_id") Long songId) throws AlbumNotFoundException, SongNotFoundException {
        Album album = albumService.addSong(albumId, songId);
        return ResponseEntity.ok(album);
    }
}
