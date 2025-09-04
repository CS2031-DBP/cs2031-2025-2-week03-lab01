package org.lab.week03lab01.controller;

import jakarta.validation.Valid;
import org.lab.week03lab01.exceptions.SongNotFoundException;
import org.lab.week03lab01.model.CreateSongDTO;
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
    public ResponseEntity<Song> createSong(@Valid @RequestBody CreateSongDTO dto) {
        Song savedSong = songService.save(dto);
        return ResponseEntity.ok(savedSong);
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() throws Exception {
        List<Song> songs = songService.findAll();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) throws SongNotFoundException {
        Song song = songService.findById(id);
        return ResponseEntity.ok(song);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
