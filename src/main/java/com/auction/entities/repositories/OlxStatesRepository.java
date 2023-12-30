package com.auction.entities.repositories;

import com.auction.entities.models.OlxStates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OlxStatesRepository extends JpaRepository<OlxStates, UUID> {
    Optional<OlxStates> findByUrl(String url);
}