package org.lab.week03lab01.service;

import org.lab.week03lab01.exceptions.ConflictException;
import org.lab.week03lab01.exceptions.ResourceNotFoundException;
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

        Song song = songRepository.findById(songId).orElseThrow();
        Album album = albumRepository.findById(albumId).orElseThrow();


        /// Add 404 custom exception (Delete code above when uncommenting)
        /*
        Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found with id: " + songId));

        Album album = albumRepository.findById(albumId).orElseThrow(() -> new ResourceNotFoundException("Album not found with id: " + albumId));
        */

        /// Add 409 custom exception when song is already in tha album songs array
        /*
        if (album.getSongs().contains(song)) {
            throw new ConflictException("Song with id: " + songId + " is already in the album with id: " + albumId);
        }
        */

        album.getSongs().add(song);

        return albumRepository.save(album);
    }
}
