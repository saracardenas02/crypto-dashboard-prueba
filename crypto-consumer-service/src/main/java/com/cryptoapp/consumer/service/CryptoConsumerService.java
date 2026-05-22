package com.cryptoapp.consumer.service;


import com.cryptoapp.consumer.client.CoinGeckoClientInterface;
import com.cryptoapp.consumer.model.CryptoItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CryptoConsumerService implements CryptoConsumerServiceInterface {

    private final CoinGeckoClientInterface coinGeckoClient;

    private final List<CryptoItem> cache = new CopyOnWriteArrayList<>();

    @Value("${coingecko.api.coins-limit}")
    private int coinsLimit;

    public CryptoConsumerService(CoinGeckoClientInterface coinGeckoClient) {
        this.coinGeckoClient = coinGeckoClient;
    }

    @PostConstruct
    public void init() {
        refreshData();
    }

    @Scheduled(fixedDelay = 300000)
    @Override
    public void refreshData() {
        System.out.println("[CryptoConsumer] Refreshing data from CoinGecko...");
        List<CryptoItem> fresh = coinGeckoClient.fetchTopCoins(coinsLimit);
        if (!fresh.isEmpty()) {
            cache.clear();
            cache.addAll(fresh);
            System.out.println("[CryptoConsumer] Cache updated: " + cache.size() + " items.");
        } else {
            System.out.println("[CryptoConsumer] Fetch failed, keeping previous cache.");
        }
    }

    @Override
    public List<CryptoItem> getAllItems() {
        return Collections.unmodifiableList(cache);
    }

    @Override
    public Optional<CryptoItem> getItemById(String id) {
        return cache.stream()
                .filter(item -> item.getId().equalsIgnoreCase(id))
                .findFirst();
    }

}
