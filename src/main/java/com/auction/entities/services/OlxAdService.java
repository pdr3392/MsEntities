package com.auction.entities.services;

import com.auction.entities.dtos.OlxAdKafkaRecordDTO;
import com.auction.entities.dtos.OlxAdRecordDTO;
import com.auction.entities.models.OlxAd;
import com.auction.entities.repositories.OlxAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public OlxAd createOlxAdFromKafka(OlxAdKafkaRecordDTO olxAdKafkaRecordDTO) {
        OlxAd olxAd = new OlxAd(
                null,
                olxAdKafkaRecordDTO.price(),
                olxAdKafkaRecordDTO.url(),
                LocalDateTime.now(),
                null,
                olxAdKafkaRecordDTO.publishedAt(),
                olxAdKafkaRecordDTO.city(),
                olxAdKafkaRecordDTO.state(),
                olxAdKafkaRecordDTO.isActive()
        );

        return olxAdRepository.save(olxAd);
    }

    public List<String> getUnvisitedUrls(List<String> urls) {
        Set<String> unvisitedUrls = new HashSet<>(urls);
        int batchSize = 500;

        for (int i = 0; i < urls.size(); i += batchSize) {
            List<String> batch = urls.subList(i, Math.min(urls.size(), i + batchSize));
            List<OlxAd> visitedAds = olxAdRepository.findByUrlIn(batch);
            visitedAds.forEach(ad -> unvisitedUrls.remove(ad.getUrl()));
        }

        return new ArrayList<>(unvisitedUrls);
    }
}
