package org.lab.week03lab01.model;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CreateAlbumDTO {
    @NotNull
    private String title;
    @NotNull
    private Date releaseDate;
    @NotNull
    private String label;

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
