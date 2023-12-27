package com.auction.entities.controllers;

import com.auction.entities.dtos.OlxAdRecordDTO;
import com.auction.entities.models.OlxAd;
import com.auction.entities.services.OlxAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/olxAds")
public class OlxAdController {

    private final OlxAdService olxAdService;

    @Autowired
    public OlxAdController(OlxAdService olxAdService) {
        this.olxAdService = olxAdService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OlxAd createOlxAd(@RequestBody OlxAdRecordDTO olxAdDto) {
        return olxAdService.createOlxAd(olxAdDto);
    }

    @PutMapping("/{id}")
    public OlxAd updateOlxAd(@PathVariable UUID id, @RequestBody OlxAdRecordDTO olxAdDto) {
        return olxAdService.updateOlxAd(id, olxAdDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOlxAd(@PathVariable UUID id) {
        olxAdService.deleteOlxAd(id);
    }

    @GetMapping("/{id}")
    public OlxAd getOlxAdById(@PathVariable UUID id) {
        return olxAdService.getOlxAdById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OlxAd not found"));
    }

    @GetMapping
    public Map<String, Object> getAllOlxAdsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        Page<OlxAd> olxAdsPage = olxAdService.getAllOlxAdsPaginated(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("olxAds", olxAdsPage.getContent());

        if(olxAdsPage.hasNext()) {
            String nextPageUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .queryParam("page", page + 1)
                    .queryParam("size", size)
                    .toUriString();
            response.put("next", nextPageUrl);
        }

        return response;
    }
}
