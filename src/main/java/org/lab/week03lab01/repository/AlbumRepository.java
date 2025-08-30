package org.lab.week03lab01.repository;

import org.lab.week03lab01.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
