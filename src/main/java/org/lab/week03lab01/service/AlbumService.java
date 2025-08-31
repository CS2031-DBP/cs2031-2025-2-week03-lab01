package org.lab.week03lab01.service;

import org.lab.week03lab01.exceptions.AlbumNotFoundException;
import org.lab.week03lab01.exceptions.SongNotFoundException;
import org.lab.week03lab01.model.Album;
import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.repository.AlbumRepository;
import org.lab.week03lab01.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongService songService;

    public AlbumService(AlbumRepository albumRepository, SongService songService){
        this.albumRepository = albumRepository;
        this.songService = songService;
    }

    public List<Album> findAll(){
        return albumRepository.findAll();
    }

    public Album findById(Long id)  throws AlbumNotFoundException {
        return albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
    }

    public Album createAlbum(Album newAlbum) {
        return albumRepository.save(newAlbum);
    }

    public Album addSong(Long albumId, Long songId) throws AlbumNotFoundException, SongNotFoundException {
        /// Add 404 custom exception
        Album album = this.findById(albumId);
        Song song = songService.findById(songId);

        /// Add 409 custom exception when song is already in tha album songs array
        album.getSongs().add(song);

        return albumRepository.save(album);
    }
}
