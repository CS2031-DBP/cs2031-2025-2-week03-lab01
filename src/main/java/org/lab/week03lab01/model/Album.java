package org.lab.week03lab01.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Album {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Date releaseDate;
    private String label;

    public Album() {
    }

    public Album(String title, Date releaseDate, String label) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
