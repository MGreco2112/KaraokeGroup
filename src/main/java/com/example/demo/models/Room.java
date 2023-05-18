package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Guest host;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("room")
    private Set<SpotifySong> songs = new HashSet<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("room")
    private Set<Guest> guests = new HashSet<>();

    public Room() {
    }

    public Room(Guest host) {
        this.host = host;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Guest getHost() {
        return host;
    }

    public void setHost(Guest host) {
        this.host = host;
    }

    public Set<SpotifySong> getSongs() {
        return songs;
    }

    public void setSongs(Set<SpotifySong> songs) {
        this.songs = songs;
    }

    public void addSong(SpotifySong song) {
        songs.add(song);
    }

    public void removeSong(SpotifySong song) {
        songs.remove(song);
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void removeGuest(Guest guest) {
        guests.remove(guest);
    }
}
