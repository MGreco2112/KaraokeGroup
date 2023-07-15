package com.example.demo.controllers;

import com.example.demo.models.Room;
import com.example.demo.models.SpotifySong;
import com.example.demo.payload.request.NewSongRequest;
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
@RequestMapping("/api/spotify/songs")
public class SpotifySongController {
    @Autowired
    private SpotifySongRepository repository;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/all")
    public List<SpotifySong> getAllSongs() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SpotifySong> getSongById(@PathVariable Long id) {
        SpotifySong selSong = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selSong);
    }

    @GetMapping("room/id/{id}")
    public ResponseEntity<List<SpotifySong>> getSongsByRoomId(@PathVariable Long id) {
        List<SpotifySong> selSongs = repository.findSongsByRoomId(id);

        return ResponseEntity.ok(selSongs);
    }

    @PostMapping()
    public ResponseEntity<SpotifySong> postNewSong(@RequestBody NewSongRequest request) {

        Room selRoom = roomRepository.findById(request.getRoom().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SpotifySong newSong = new SpotifySong(
                request.getSpotifySongURL(),
                request.getName(),
                selRoom
        );

        return new ResponseEntity<>(repository.save(newSong), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteSongById(@PathVariable Long id) {
        SpotifySong selSong = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(selSong);

        return ResponseEntity.ok("Song Deleted");
    }
}
