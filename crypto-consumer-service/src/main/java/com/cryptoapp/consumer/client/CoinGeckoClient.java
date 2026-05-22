package com.cryptoapp.consumer.client;

import com.cryptoapp.consumer.model.CryptoItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CoinGeckoClient implements CoinGeckoClientInterface {

    private final RestTemplate restTemplate;

    @Value("${coingecko.api.base-url}")
    private String baseUrl;

    public CoinGeckoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CryptoItem> fetchTopCoins(int limit) {

        String url = baseUrl + "/coins/markets"
                + "?vs_currency=usd"
                + "&order=market_cap_desc"
                + "&per_page=" + limit
                + "&page=1"
                + "&sparkline=false";

        try {
            CryptoItem[] items = restTemplate.getForObject(url, CryptoItem[].class);
            if (items == null) return Collections.emptyList();
            return Arrays.asList(items);
        } catch (Exception e) {
            System.err.println("Error fetching from CoinGecko: " + e.getMessage());
            return Collections.emptyList();
        }
    }

}
