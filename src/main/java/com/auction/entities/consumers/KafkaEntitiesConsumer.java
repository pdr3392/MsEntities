package com.auction.entities.consumers;

import com.auction.entities.dtos.OlxAdKafkaRecordDTO;
import com.auction.entities.services.OlxAdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEntitiesConsumer {

    private final ObjectMapper objectMapper;
    private final OlxAdService olxAdService;

    @Autowired
    public KafkaEntitiesConsumer(ObjectMapper objectMapper, OlxAdService olxAdService) {
        this.objectMapper = objectMapper;
        this.olxAdService = olxAdService;
    }

    @KafkaListener(topics = "newEntity", groupId = "entities")
    public void listen(String message) {
        try {
            OlxAdKafkaRecordDTO olxAdRecordDTO = objectMapper.readValue(message, OlxAdKafkaRecordDTO.class);
            olxAdService.createOlxAdFromKafka(olxAdRecordDTO);
        } catch (Exception e) {
            System.out.println("Error processing message: " + e.getMessage());
        }
    }
}
