package com.example.demo.payload.request;

public class NewSongRequest {
    private String spotifySongURL;
    private String name;

    public NewSongRequest() {
    }

    public NewSongRequest(String spotifySongURL, String name) {
        this.spotifySongURL = spotifySongURL;
        this.name = name;
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
}
