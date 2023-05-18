package com.example.demo.controllers;

import com.example.demo.models.Room;
import com.example.demo.repositories.GuestRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.SpotifySongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomRepository repository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private SpotifySongRepository songRepository;

    @GetMapping()
    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room selRoom = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selRoom);
    }

    @GetMapping("/host/id/{id}")
    public ResponseEntity<Room> getRoomByHostId(@PathVariable Long id) {
        Room selRoom = repository.findRoomByHostId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selRoom);
    }

    //TODO create Post, Update, and Create routes
}
