package com.auction.entities.services;

import com.auction.entities.dtos.OlxAdRecordDTO;
import com.auction.entities.models.OlxAd;
import com.auction.entities.repositories.OlxAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OlxAdService {

    @Autowired
    private final OlxAdRepository olxAdRepository;


    public OlxAdService(OlxAdRepository olxAdRepository) {
        this.olxAdRepository = olxAdRepository;
    }

    public Optional<OlxAd> getOlxAdById(UUID id) {
        return olxAdRepository.findById(id);
    }

    public OlxAd createOlxAd(OlxAdRecordDTO olxAdDto) {
        OlxAd olxAd = new OlxAd(
                olxAdDto.id(),
                olxAdDto.price(),
                olxAdDto.url(),
                olxAdDto.createdAt(),
                olxAdDto.updatedAt(),
                olxAdDto.publishedAt(),
                olxAdDto.city(),
                olxAdDto.state(),
                olxAdDto.isActive()
        );

        return olxAdRepository.save(olxAd);
    }

    public OlxAd updateOlxAd(UUID id, OlxAdRecordDTO olxAdDto) {
        OlxAd olxAd = new OlxAd(
                olxAdDto.id(),
                olxAdDto.price(),
                olxAdDto.url(),
                olxAdDto.createdAt(),
                olxAdDto.updatedAt(),
                olxAdDto.publishedAt(),
                olxAdDto.city(),
                olxAdDto.state(),
                olxAdDto.isActive()
        );

        return olxAdRepository.save(olxAd);
    }

    public void deleteOlxAd(UUID id) {
        olxAdRepository.deleteById(id);
    }

    public Page<OlxAd> getAllOlxAdsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return olxAdRepository.findAll(pageable);
    }
}
