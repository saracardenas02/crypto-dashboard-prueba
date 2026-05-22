package com.cryptoapp.query.service;

import com.cryptoapp.query.client.ConsumerClientInterface;
import com.cryptoapp.query.model.CryptoItem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoQueryService implements CryptoQueryServiceInterface {

    private final ConsumerClientInterface consumerClient;

    public CryptoQueryService(ConsumerClientInterface consumerClient) {
        this.consumerClient = consumerClient;
    }

    @Cacheable(value = "crypto-list")
    @Override
    public List<CryptoItem> getAllItems() {
        System.out.println("[QueryService] Cache MISS - fetching from consumer...");
        return consumerClient.getAll();
    }

    @Cacheable(value = "crypto-item", key = "#id", unless = "#result == null || !#result.isPresent()")
    @Override
    public Optional<CryptoItem> getItemById(String id) {
        System.out.println("[QueryService] Cache MISS for id: " + id);
        return consumerClient.getById(id);
    }

    @CacheEvict(value = {"crypto-list", "crypto-item"}, allEntries = true)
    @Scheduled(fixedDelay = 300000)
    public void evictCache() {
        System.out.println("[QueryService] Cache evicted - fresh data on next request.");
    }


}
