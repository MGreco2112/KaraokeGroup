package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isHost = false;

    //possibly generate random hash to use as display name instead of raw database generated id

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"guests", "host"})
    private Room room;

    @OneToOne
    @JsonIgnoreProperties("host")
    private Room hostRoom;

    public Guest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getHostRoom() {
        return hostRoom;
    }

    public void setHostRoom(Room hostRoom) {
        this.hostRoom = hostRoom;
    }

    public boolean getIHost() {
        return isHost;
    }

    public void setIsHost(boolean host) {
        isHost = host;
    }
}
