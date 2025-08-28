package org.lab.week03lab01.repository;

import org.lab.week03lab01.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
