package com.example.demo.controllers;

import com.example.demo.models.Guest;
import com.example.demo.models.Room;
import com.example.demo.models.SpotifySong;
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

    @PostMapping("/new/host/id/{id}")
    public ResponseEntity<Room> createRoomWithHostID(@PathVariable Long id) {

        Guest newHost = guestRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Room newRoom = new Room(newHost);

        repository.save(newRoom);
        newHost.setHostRoom(newRoom);
        newHost.setIsHost(true);
        guestRepository.save(newHost);

        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @PutMapping("/add/guest/{gId}/room/{rId}")
    public ResponseEntity<Room> addGuestToRoom(@PathVariable Long gId, @PathVariable Long rId) {
        Guest selGuest = guestRepository.findById(gId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Room selRoom = repository.findById(rId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.addGuest(selGuest);
        repository.save(selRoom);
        selGuest.setRoom(selRoom);
        guestRepository.save(selGuest);

        return ResponseEntity.ok(selRoom);
    }

    @PutMapping("/remove/guest/{gId}/room/{rId}")
    public ResponseEntity<Room> removeGuestFromRoom(@PathVariable Long gId, @PathVariable Long rId) {
        Guest selGuest = guestRepository.findById(gId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Room selRoom = repository.findById(rId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.removeGuest(selGuest);
        repository.save(selRoom);
        selGuest.setRoom(null);
        guestRepository.save(selGuest);

        return ResponseEntity.ok(selRoom);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteRoomById(@PathVariable Long id) {
        Room selRoom = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Guest host = selRoom.getHost();
        host.setRoom(null);
        guestRepository.save(host);

        for (Guest guest : selRoom.getGuests()) {
            guest.setRoom(null);
            guestRepository.save(guest);
        }

        for (SpotifySong song : selRoom.getSongs()) {
            song.setRoom(null);
            songRepository.save(song);
        }

        repository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }
}
