package com.cryptoapp.management.services;

import com.cryptoapp.management.client.ConsumerClientInterface;
import com.cryptoapp.management.model.CryptoItem;
import com.cryptoapp.management.model.WatchlistEntry;
import com.cryptoapp.management.service.WatchlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock
    private ConsumerClientInterface consumerClient;

    @InjectMocks
    private WatchlistService watchlistService;

    private CryptoItem bitcoin;

    @BeforeEach
    void setUp() {
        bitcoin = new CryptoItem("bitcoin", "btc", "Bitcoin", 65000.0,
                1200000000000L, 2.5, 30000000000L, "img", "2026-05-01T00:00:00Z");
    }

    @Test
    void addToWatchlist_validId_returnsEntry() {
        when(consumerClient.getById("bitcoin")).thenReturn(Optional.of(bitcoin));

        Optional<WatchlistEntry> result = watchlistService.addToWatchlist("bitcoin");

        assertTrue(result.isPresent());
        assertEquals("bitcoin", result.get().getCryptoId());
        assertEquals("Bitcoin", result.get().getName());
    }

    @Test
    void addToWatchlist_invalidId_returnsEmpty() {
        when(consumerClient.getById("fakecoin")).thenReturn(Optional.empty());

        Optional<WatchlistEntry> result = watchlistService.addToWatchlist("fakecoin");

        assertTrue(result.isEmpty());
    }

    @Test
    void addToWatchlist_duplicate_doesNotAddTwice() {
        when(consumerClient.getById("bitcoin")).thenReturn(Optional.of(bitcoin));
        watchlistService.addToWatchlist("bitcoin");
        watchlistService.addToWatchlist("bitcoin");

        List<WatchlistEntry> watchlist = watchlistService.getWatchlist();

        assertEquals(1, watchlist.size());
    }

    @Test
    void removeFromWatchlist_existing_returnsTrue() {
        when(consumerClient.getById("bitcoin")).thenReturn(Optional.of(bitcoin));
        watchlistService.addToWatchlist("bitcoin");

        boolean removed = watchlistService.removeFromWatchlist("bitcoin");

        assertTrue(removed);
        assertEquals(0, watchlistService.getWatchlist().size());
    }

    @Test
    void removeFromWatchlist_nonExisting_returnsFalse() {
        boolean removed = watchlistService.removeFromWatchlist("ethereum");

        assertFalse(removed);
    }

}

