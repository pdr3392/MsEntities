package com.auction.entities.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OlxAdRecordDTO(
        UUID id,
        BigDecimal price,
        String url,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime publishedAt,
        String city,
        String state,
        Boolean isActive
) {
}
