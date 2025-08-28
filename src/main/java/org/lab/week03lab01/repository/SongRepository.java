package org.lab.week03lab01.repository;

import org.lab.week03lab01.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
