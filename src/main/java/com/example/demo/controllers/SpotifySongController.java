package com.example.demo.controllers;

import com.example.demo.models.SpotifySong;
import com.example.demo.payload.request.NewSongRequest;
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

    @GetMapping("/all")
    public List<SpotifySong> getAllSongs() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SpotifySong> getSongById(@PathVariable Long id) {
        SpotifySong selSong = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selSong);
    }

    @PostMapping()
    public ResponseEntity<SpotifySong> postNewSong(@RequestBody NewSongRequest request) {
        SpotifySong newSong = new SpotifySong(
                request.getSpotifySongURL()
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
