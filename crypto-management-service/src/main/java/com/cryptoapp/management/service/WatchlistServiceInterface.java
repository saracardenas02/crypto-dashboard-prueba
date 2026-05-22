package com.cryptoapp.management.service;

import com.cryptoapp.management.model.WatchlistEntry;
import java.util.List;
import java.util.Optional;

public interface WatchlistServiceInterface {

    List<WatchlistEntry> getWatchlist();

    Optional<WatchlistEntry> addToWatchlist(String cryptoId);

    boolean removeFromWatchlist(String cryptoId);

}

