package com.auction.entities.dtos;

import java.util.List;

public record UnvisitedUrlsRecordDTO(
        String correlationId,
        List<String> urlsToCheck
) {
}