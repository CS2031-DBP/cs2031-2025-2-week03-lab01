package org.lab.week03lab01.repository;

import org.lab.week03lab01.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
