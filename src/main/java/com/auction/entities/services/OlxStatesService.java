package com.auction.entities.services;

import com.auction.entities.models.OlxStates;
import com.auction.entities.repositories.OlxStatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OlxStatesService {
    private final OlxStatesRepository olxStatesRepository;

    @Autowired
    public OlxStatesService(OlxStatesRepository olxStatesRepository) {
        this.olxStatesRepository = olxStatesRepository;
    }

    public List<OlxStates> getAllOlxStates() {
        return olxStatesRepository.findAll();
    }

    public OlxStates updateLastCrawled(String url) {
        Optional<OlxStates> olxState = olxStatesRepository.findByUrl(url);

        if (olxState.isPresent()) {
            olxState.get().setLastCrawled(LocalDateTime.now());
            return olxStatesRepository.save(olxState.get());
        }

        return null;
    }

    public String getCorrectNextState() {
        List<OlxStates> olxStates = olxStatesRepository.findAll();

        for (OlxStates olxState : olxStates) {
            if (olxState.getLastCrawled() == null) {
                return olxState.getUrl();
            }
        }

        OlxStates oldestCrawledState = olxStates.stream()
                .min(Comparator.comparing(OlxStates::getLastCrawled))
                .orElseThrow(() -> new IllegalStateException("No OlxStates found"));

        return oldestCrawledState.getUrl();
    }
}
