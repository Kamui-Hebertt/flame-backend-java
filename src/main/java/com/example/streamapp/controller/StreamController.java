package com.example.streamapp.controller;

import com.example.streamapp.model.StreamSession;
import com.example.streamapp.repository.StreamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/streams")
public class StreamController {
    
    private final StreamRepository streamRepository;
    
    public StreamController(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }
    
    // Get all streams
    @GetMapping
    public List<StreamSession> getAllStreams() {
        return streamRepository.findAll();
    }
    
    // Get only LIVE streams
    @GetMapping("/live")
    public List<StreamSession> getLiveStreams() {
        return streamRepository.findByIsLive(true);
    }
    
    // Start a new stream
    @PostMapping("/start")
    public ResponseEntity<StreamSession> startStream(@RequestBody StreamSession stream) {
        stream.setLive(true);
        stream.setStartTime(LocalDateTime.now());
        return ResponseEntity.ok(streamRepository.save(stream));
    }
    
    // Stop a stream by ID
    @PostMapping("/stop/{id}")
    public ResponseEntity<StreamSession> stopStream(@PathVariable Long id) {
        return streamRepository.findById(id)
            .map(stream -> {
                stream.setLive(false);
                stream.setEndTime(LocalDateTime.now());
                return ResponseEntity.ok(streamRepository.save(stream));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Check if a stream key is valid
    @GetMapping("/validate/{streamKey}")
    public ResponseEntity<Boolean> validateStreamKey(@PathVariable String streamKey) {
        return ResponseEntity.ok(streamRepository.findByStreamKey(streamKey) != null);
    }
}