package com.cryptoapp.consumer.controller;

import com.cryptoapp.consumer.model.CryptoItem;
import com.cryptoapp.consumer.service.CryptoConsumerServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/internal/crypto")
public class InternalCryptoController {

    private final CryptoConsumerServiceInterface service;

    public InternalCryptoController(CryptoConsumerServiceInterface service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CryptoItem>> getAll() {
        List<CryptoItem> items = service.getAllItems();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 vacio
        }
        return ResponseEntity.ok(items); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<CryptoItem> getById(@PathVariable String id) {
        return service.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // 404 not found
    }

}
