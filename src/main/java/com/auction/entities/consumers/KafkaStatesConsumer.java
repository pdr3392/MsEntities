package com.auction.entities.consumers;

import com.auction.entities.dtos.OlxStatesKafkaRecordDTO;
import com.auction.entities.services.OlxStatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaStatesConsumer {
    private final OlxStatesService olxStatesService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaStatesConsumer(OlxStatesService olxStatesService, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.olxStatesService = olxStatesService;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "getNextState", groupId = "states")
    public void getNextStateEvent(String message) {
        System.out.println("Received getNextState event with message: " + message);
        try {
            OlxStatesKafkaRecordDTO requestDTO = objectMapper.readValue(message, OlxStatesKafkaRecordDTO.class);
            String nextState = olxStatesService.getCorrectNextState();

            OlxStatesKafkaRecordDTO responseDTO = new OlxStatesKafkaRecordDTO(
                    requestDTO.correlationId(),
                    nextState
            );

            String jsonResponse = objectMapper.writeValueAsString(responseDTO);
            kafkaTemplate.send("nextState", jsonResponse);
        } catch (Exception e) {
            System.out.println("Error processing message: " + e.getMessage());
        }
    }
}
