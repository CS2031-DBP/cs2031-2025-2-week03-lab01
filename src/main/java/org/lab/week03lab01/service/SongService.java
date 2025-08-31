package org.lab.week03lab01.service;

import org.lab.week03lab01.exceptions.SongNotFoundException;
import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song save(Song song) {
        if (song != null && song.getTitle() != null && !song.getTitle().isEmpty()) {
             return songRepository.save(song);
        } else {
            throw new IllegalArgumentException("Song title cannot be null or empty");
        }
    }

    public Song findById(Long id) throws SongNotFoundException {
        return songRepository.findById(id).orElseThrow(() -> new SongNotFoundException(id));
    }

    public List<Song> findAll(){
        return songRepository.findAll();
    }

    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }
}
