package com.example.demo.payload.request;

public class NewSongRequest {
    private String spotifySongURL;

    public NewSongRequest() {
    }

    public NewSongRequest(String spotifySongURL) {
        this.spotifySongURL = spotifySongURL;
    }

    public String getSpotifySongURL() {
        return spotifySongURL;
    }

    public void setSpotifySongURL(String spotifySongURL) {
        this.spotifySongURL = spotifySongURL;
    }
}
