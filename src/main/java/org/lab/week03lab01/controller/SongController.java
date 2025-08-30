package org.lab.week03lab01.controller;

import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song savedSong = songService.save(song);
        return ResponseEntity.ok(savedSong);
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.findAll();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Song song = songService.findById(id);
        if (song != null) {
            return ResponseEntity.ok(song);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
