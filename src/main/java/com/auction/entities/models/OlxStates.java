package com.auction.entities.models;

import com.auction.entities.enums.BrazilianState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OlxStates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    @Column(length = 500, unique = true)
    private String url;

    private LocalDateTime lastCrawled;

    @Enumerated(EnumType.STRING)
    private BrazilianState state;
}