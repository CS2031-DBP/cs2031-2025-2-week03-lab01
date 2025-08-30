package org.lab.week03lab01.service;

import org.lab.week03lab01.model.Album;
import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.repository.AlbumRepository;
import org.lab.week03lab01.repository.SongRepository;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public AlbumService(AlbumRepository albumRepository, SongRepository songRepository){
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    public Album createAlbum(Album newAlbum) {
        return albumRepository.save(newAlbum);
    }

    public Album addSong(Long albumId, Long songId) {
        /// Add 404 custom exception
        Song song = songRepository.findById(songId).orElseThrow();
        Album album = albumRepository.findById(albumId).orElseThrow();

        /// Add 409 custom exception when song is already in tha album songs array
        album.getSongs().add(song);

        return albumRepository.save(album);
    }
}
