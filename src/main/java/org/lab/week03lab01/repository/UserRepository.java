package org.lab.week03lab01.repository;

import org.lab.week03lab01.model.Song;
import org.lab.week03lab01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
