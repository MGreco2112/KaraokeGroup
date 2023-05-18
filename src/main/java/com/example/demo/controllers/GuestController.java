package com.example.demo.controllers;

import com.example.demo.models.Guest;
import com.example.demo.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/guest")
public class GuestController {
    @Autowired
    private GuestRepository repository;

    @GetMapping("/all")
    public List<Guest> getAllGuests() {
        return repository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        Guest selGuest = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selGuest);
    }

    @PostMapping()
    public ResponseEntity<Guest> createNewGuest() {
        Guest newGuest = new Guest();

        return new ResponseEntity<>(repository.save(newGuest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteGuestById(@PathVariable Long id) {
        Guest selGuest = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(selGuest);

        return ResponseEntity.ok("Guest Deleted");
    }
}
