package com.example.demo.payload.request;

import com.example.demo.models.Room;
import jakarta.persistence.OneToOne;

public class NewSongRequest {
    private String spotifySongURL;
    private String name;

    private Room room;

    public NewSongRequest() {
    }

    public NewSongRequest(String spotifySongURL, String name, Room room) {
        this.spotifySongURL = spotifySongURL;
        this.name = name;
        this.room = room;
    }

    public String getSpotifySongURL() {
        return spotifySongURL;
    }

    public void setSpotifySongURL(String spotifySongURL) {
        this.spotifySongURL = spotifySongURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
