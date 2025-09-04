package org.lab.week03lab01.service;

import org.lab.week03lab01.exceptions.SongNotFoundException;
import org.lab.week03lab01.model.CreateSongDTO;
import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    public SongService(SongRepository songRepository, ModelMapper modelMapper) {
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
    }

    public Song save(CreateSongDTO dto) {
        var newSong = new Song();
        modelMapper.map(dto, newSong);
        return songRepository.save(newSong);
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
