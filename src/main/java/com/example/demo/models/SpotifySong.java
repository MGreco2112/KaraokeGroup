package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class SpotifySong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    private String spotifySongURL;

    public SpotifySong() {
    }

    public SpotifySong(String spotifySongURL) {
        this.spotifySongURL = spotifySongURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpotifySongURL() {
        return spotifySongURL;
    }

    public void setSpotifySongURL(String spotifySongURL) {
        this.spotifySongURL = spotifySongURL;
    }
}
