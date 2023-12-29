package com.auction.entities.services;

import com.auction.entities.models.OlxStates;
import com.auction.entities.repositories.OlxStatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public OlxStates updateLastCrawled(UUID id, LocalDateTime lastCrawled) {
        Optional<OlxStates> olxStateOptional = olxStatesRepository.findById(id);
        if (olxStateOptional.isPresent()) {
            OlxStates olxState = olxStateOptional.get();
            olxState.setLastCrawled(lastCrawled);
            return olxStatesRepository.save(olxState);
        } else {
            return null;
        }
    }
}
