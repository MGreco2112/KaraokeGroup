package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class SpotifySong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    private String spotifySongURL;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIgnoreProperties("songs")
    private Room room;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
