package com.example.streamapp.repository;

import com.example.streamapp.model.StreamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StreamRepository extends JpaRepository<StreamSession, Long> {
    StreamSession findByStreamKey(String streamKey);
    List<StreamSession> findByIsLive(boolean isLive);
}