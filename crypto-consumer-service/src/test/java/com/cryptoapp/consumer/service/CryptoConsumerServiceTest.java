package com.cryptoapp.consumer.service;


import com.cryptoapp.consumer.client.CoinGeckoClientInterface;
import com.cryptoapp.consumer.model.CryptoItem;
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
class CryptoConsumerServiceTest {

    @Mock
    private CoinGeckoClientInterface coinGeckoClient;

    @InjectMocks
    private CryptoConsumerService service;

    private CryptoItem bitcoin;
    private CryptoItem ethereum;

    @BeforeEach
    void setUp() {
        bitcoin = new CryptoItem("bitcoin", "btc", "Bitcoin", 65000.0,
                1200000000000L, 2.5, 30000000000L, "img", "2026-05-01T00:00:00Z");
        ethereum = new CryptoItem("ethereum", "eth", "Ethereum", 3200.0,
                380000000000L, 1.8, 15000000000L, "img", "2026-05-01T00:00:00Z");
    }

    @Test
    void getAllItems_afterRefresh_returnsAllItems() {
        when(coinGeckoClient.fetchTopCoins(anyInt())).thenReturn(List.of(bitcoin, ethereum));
        service.refreshData();

        List<CryptoItem> result = service.getAllItems();

        assertEquals(2, result.size());
        assertEquals("bitcoin", result.get(0).getId());
    }

    @Test
    void getItemById_existingId_returnsItem() {
        when(coinGeckoClient.fetchTopCoins(anyInt())).thenReturn(List.of(bitcoin, ethereum));
        service.refreshData();

        Optional<CryptoItem> result = service.getItemById("ethereum");

        assertTrue(result.isPresent());
        assertEquals("Ethereum", result.get().getName());
    }

    @Test
    void getItemById_nonExistingId_returnsEmpty() {
        when(coinGeckoClient.fetchTopCoins(anyInt())).thenReturn(List.of(bitcoin));
        service.refreshData();

        Optional<CryptoItem> result = service.getItemById("solana");

        assertTrue(result.isEmpty());
    }

    @Test
    void refreshData_whenFetchFails_keepsPreviousCache() {
        // Primera carga exitosa
        when(coinGeckoClient.fetchTopCoins(anyInt())).thenReturn(List.of(bitcoin));
        service.refreshData();

        // Segunda carga falla: CoinGecko devuelve lista vacia
        when(coinGeckoClient.fetchTopCoins(anyInt())).thenReturn(List.of());
        service.refreshData();

        // El cache no se borro: sigue teniendo el dato anterior
        assertEquals(1, service.getAllItems().size());
    }

}
