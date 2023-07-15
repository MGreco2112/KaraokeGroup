package com.example.demo.repositories;

import com.example.demo.models.SpotifySong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotifySongRepository extends JpaRepository<SpotifySong, Long> {
        @Query(value = "SELECT * FROM spotify_song WHERE room_id = :id", nativeQuery = true)
        public List<SpotifySong> findSongsByRoomId(@Param("id") Long id);
}
