package com.auction.entities.consumers;

import com.auction.entities.dtos.OlxAdKafkaRecordDTO;
import com.auction.entities.dtos.UnvisitedUrlsRecordDTO;
import com.auction.entities.services.OlxAdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaEntitiesConsumer {

    private final ObjectMapper objectMapper;
    private final OlxAdService olxAdService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaEntitiesConsumer(ObjectMapper objectMapper, OlxAdService olxAdService, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.olxAdService = olxAdService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "newEntity", groupId = "entities")
    public void newEntityEvent(String message) {
        try {
            OlxAdKafkaRecordDTO olxAdRecordDTO = objectMapper.readValue(message, OlxAdKafkaRecordDTO.class);
            olxAdService.createOlxAdFromKafka(olxAdRecordDTO);
        } catch (Exception e) {
            System.out.println("Error processing message: " + e.getMessage());
        }
    }


    @KafkaListener(topics = "unvisitedUrls", groupId = "entities")
    public void checkUnvisitedUrlsEvent(String message) {
        try {
            UnvisitedUrlsRecordDTO requestDTO = objectMapper.readValue(message, UnvisitedUrlsRecordDTO.class);
            List<String> unvisitedUrls = olxAdService.getUnvisitedUrls(requestDTO.urlsToCheck());

            UnvisitedUrlsRecordDTO responseDTO = new UnvisitedUrlsRecordDTO(
                    requestDTO.correlationId(),
                    unvisitedUrls
            );

            String jsonResponse = objectMapper.writeValueAsString(responseDTO);
            kafkaTemplate.send("unvisitedUrlsResponse", jsonResponse);
        } catch (Exception e) {
            System.out.println("Error processing message: " + e.getMessage());
        }
    }
}

