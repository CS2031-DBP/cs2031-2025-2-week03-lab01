package org.lab.week03lab01.service;

import org.lab.week03lab01.exceptions.AlbumNotFoundException;
import org.lab.week03lab01.model.Album;
import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.repository.AlbumRepository;
import org.lab.week03lab01.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public AlbumService(AlbumRepository albumRepository, SongRepository songRepository){
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    public List<Album> findAll(){
        return albumRepository.findAll();
    }

    public Album findById(Long id){
        return albumRepository.findById(id).orElse(null);
    }

    public Album createAlbum(Album newAlbum) {
        return albumRepository.save(newAlbum);
    }

    public Album addSong(Long albumId, Long songId) throws AlbumNotFoundException {
        /// Add 404 custom exception
        Song song = songRepository.findById(songId).orElseThrow();
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(albumId));

        /// Add 409 custom exception when song is already in tha album songs array
        album.getSongs().add(song);

        return albumRepository.save(album);
    }
}
