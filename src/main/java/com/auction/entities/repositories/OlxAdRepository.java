package com.auction.entities.repositories;

import com.auction.entities.models.OlxAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OlxAdRepository extends JpaRepository<OlxAd, UUID> {
}