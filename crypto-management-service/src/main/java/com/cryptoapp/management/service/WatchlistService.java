package com.cryptoapp.management.service;

import com.cryptoapp.management.client.ConsumerClientInterface;
import com.cryptoapp.management.model.CryptoItem;
import com.cryptoapp.management.model.WatchlistEntry;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WatchlistService implements WatchlistServiceInterface {

    private final ConsumerClientInterface consumerClient;

    private final List<WatchlistEntry> watchlist = new CopyOnWriteArrayList<>();

    public WatchlistService(ConsumerClientInterface consumerClient) {
        this.consumerClient = consumerClient;
    }

    @Override
    public List<WatchlistEntry> getWatchlist() {
        return Collections.unmodifiableList(watchlist);
    }

    @Override
    public Optional<WatchlistEntry> addToWatchlist(String cryptoId) {
        boolean alreadyAdded = watchlist.stream()
                .anyMatch(e -> e.getCryptoId().equalsIgnoreCase(cryptoId));
        if (alreadyAdded) {
            return watchlist.stream()
                    .filter(e -> e.getCryptoId().equalsIgnoreCase(cryptoId))
                    .findFirst();
        }

        Optional<CryptoItem> itemOpt = consumerClient.getById(cryptoId);
        if (itemOpt.isEmpty()) {
            return Optional.empty();
        }

        CryptoItem item = itemOpt.get();
        String addedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        WatchlistEntry entry = new WatchlistEntry(
                item.getId(),
                item.getName(),
                item.getSymbol(),
                item.getImageUrl(),
                item.getCurrentPrice(),
                addedAt
        );

        watchlist.add(entry);
        return Optional.of(entry);
    }

    @Override
    public boolean removeFromWatchlist(String cryptoId) {
        return watchlist.removeIf(e -> e.getCryptoId().equalsIgnoreCase(cryptoId));
    }

}
