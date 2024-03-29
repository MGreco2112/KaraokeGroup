package com.example.demo.controllers;

import com.example.demo.models.Guest;
import com.example.demo.models.Room;
import com.example.demo.models.SpotifySong;
import com.example.demo.payload.request.NewSongRequest;
import com.example.demo.repositories.GuestRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.SpotifySongRepository;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    @PostMapping("/{id}/new/song")
    public ResponseEntity<Room> createSongInRoom(@PathVariable Long id, @RequestBody NewSongRequest request) {
        Room selRoom = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SpotifySong newSong = new SpotifySong(
                request.getSpotifySongURL(),
                request.getName(),
                selRoom
        );

        songRepository.save(newSong);

        selRoom.addSong(newSong);

        return ResponseEntity.ok(selRoom);
    }

    @PutMapping("/update/host/{hId}/room/{rId}")
    public ResponseEntity<Room> updateHost(@PathVariable Long hId, @PathVariable Long rId) {
        Room selRoom = repository.findById(rId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Guest newHost = guestRepository.findById(hId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Guest oldHost = guestRepository.findById(selRoom.getHost().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        oldHost.setHostRoom(null);
        oldHost.setIsHost(false);

        oldHost = guestRepository.save(oldHost);

        selRoom.addGuest(oldHost);

        newHost.setHostRoom(selRoom);
        newHost.setIsHost(true);
        newHost = guestRepository.save(newHost);

        selRoom.removeGuest(newHost);

        return ResponseEntity.ok(repository.save(selRoom));
    }

    @PutMapping("/update/host/{hId}/guest/{gId}/room/{rId}")
    public ResponseEntity<Room> exchangeHostInRoom(@PathVariable Long hId, @PathVariable Long gId, @PathVariable Long rId) {
        Room selRoom = repository.findById(rId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Guest newHost = guestRepository.findById(hId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Guest oldHost = guestRepository.findById(gId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newHost.setRoom(null);
        oldHost.setRoom(selRoom);
        oldHost.setHostRoom(null);
        selRoom.setHost(newHost);

        guestRepository.save(oldHost);
        guestRepository.save(newHost);

        return new ResponseEntity<>(repository.save(selRoom), HttpStatus.ACCEPTED);
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

    @DeleteMapping("/delete/song/id/{sId}/room/id/{rId}")
    public ResponseEntity<Room> deleteSongFromRoom(@PathVariable Long sId, @PathVariable Long rId) {
        SpotifySong selSong = songRepository.findById(sId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Room selRoom = repository.findById(rId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selSong.setRoom(null);

        songRepository.save(selSong);
        songRepository.delete(selSong);

        return ResponseEntity.ok(selRoom);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteRoomById(@PathVariable Long id) {

        List<SpotifySong> songsList = new ArrayList<>();

        Room selRoom = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Guest host = guestRepository.findById(selRoom.getHost().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        host.setHostRoom(null);
        selRoom.setHost(null);

        guestRepository.save(host);

        for (Guest guest : selRoom.getGuests()) {
            guest.setRoom(null);
            guestRepository.save(guest);
        }

        for (SpotifySong song : selRoom.getSongs()) {
            song.setRoom(null);
            songRepository.save(song);
            songsList.add(song);
        }

        repository.save(selRoom);
        repository.delete(selRoom);

        for (int i = 0; i < songsList.size(); i++) {
            songRepository.delete(songsList.get(i));
        }

        return ResponseEntity.ok("Deleted Room");
    }
}
