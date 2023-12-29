package com.auction.entities.dtos;

public record OlxStatesKafkaRecordDTO(
        String correlationId,
        String nextUrl
) {
}
