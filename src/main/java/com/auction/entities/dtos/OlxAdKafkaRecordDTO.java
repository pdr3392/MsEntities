package com.auction.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OlxAdKafkaRecordDTO(
        BigDecimal price,
        String url,
        LocalDateTime publishedAt,
        String city,
        String state,
        boolean isActive
) {
}