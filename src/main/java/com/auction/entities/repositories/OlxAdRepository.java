package com.auction.entities.repositories;

import com.auction.entities.models.OlxAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OlxAdRepository extends JpaRepository<OlxAd, UUID> {

    @Query("SELECT o FROM OlxAd o WHERE o.url IN :urls")
    List<OlxAd> findByUrlIn(@Param("urls") List<String> urls);
}