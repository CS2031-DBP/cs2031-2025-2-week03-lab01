package org.lab.week03lab01.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    private Date releaseDate;
    private Integer duration;

    // Relationships
    @ManyToOne
    private Genre genre;

    @ManyToMany
    private List<Artist> artists = new ArrayList<>();


    public Song() {
    }

    public Song(String title, Date releaseDate, Integer duration) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
