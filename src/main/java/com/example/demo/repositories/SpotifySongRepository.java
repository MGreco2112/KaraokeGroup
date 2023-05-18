package com.example.demo.repositories;

import com.example.demo.models.SpotifySong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifySongRepository extends JpaRepository<SpotifySong, Long> {

}
