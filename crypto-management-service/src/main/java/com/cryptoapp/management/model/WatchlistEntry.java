package com.cryptoapp.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistEntry {

    private String cryptoId;
    private String name;
    private String symbol;
    private String imageUrl;
    private Double currentPrice;
    private String addedAt;

}

