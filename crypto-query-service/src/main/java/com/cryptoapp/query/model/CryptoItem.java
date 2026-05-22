package com.cryptoapp.query.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String symbol;
    private String name;

    @JsonAlias("current_price")
    private Double currentPrice;

    @JsonAlias("market_cap")
    private Long marketCap;

    @JsonAlias("price_change_percentage_24h")
    private Double priceChangePercentage24h;

    @JsonAlias("total_volume")
    private Long totalVolume;

    @JsonAlias("image")
    private String imageUrl;

    @JsonAlias("last_updated")
    private String lastUpdated;

}
