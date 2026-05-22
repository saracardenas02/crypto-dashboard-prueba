package com.cryptoapp.management.controller;

import com.cryptoapp.management.model.WatchlistEntry;
import com.cryptoapp.management.service.WatchlistServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    private final WatchlistServiceInterface watchlistService;

    public WatchlistController(WatchlistServiceInterface watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping
    public ResponseEntity<List<WatchlistEntry>> getWatchlist() {
        return ResponseEntity.ok(watchlistService.getWatchlist()); // 200
    }

    @PostMapping("/{id}")
    public ResponseEntity<WatchlistEntry> addToWatchlist(@PathVariable String id) {
        return watchlistService.addToWatchlist(id)
                .map(entry -> ResponseEntity.status(201).body(entry)) // 201 Created
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable String id) {
        boolean removed = watchlistService.removeFromWatchlist(id);
        if (removed) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404
    }

}
