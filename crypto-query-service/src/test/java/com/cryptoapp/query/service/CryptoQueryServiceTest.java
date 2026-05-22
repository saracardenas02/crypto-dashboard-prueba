package com.cryptoapp.query.service;

import com.cryptoapp.query.client.ConsumerClientInterface;
import com.cryptoapp.query.model.CryptoItem;
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
class CryptoQueryServiceTest {

    @Mock
    private ConsumerClientInterface consumerClient;

    @InjectMocks
    private CryptoQueryService queryService;

    @Test
    void getAllItems_delegatesToClient_returnsItems() {
        CryptoItem item = new CryptoItem();
        item.setId("bitcoin");
        when(consumerClient.getAll()).thenReturn(List.of(item));

        List<CryptoItem> result = queryService.getAllItems();

        assertEquals(1, result.size());
        verify(consumerClient, times(1)).getAll();
    }

    @Test
    void getItemById_found_returnsItem() {
        CryptoItem item = new CryptoItem();
        item.setId("ethereum");
        when(consumerClient.getById("ethereum")).thenReturn(Optional.of(item));

        Optional<CryptoItem> result = queryService.getItemById("ethereum");

        assertTrue(result.isPresent());
        assertEquals("ethereum", result.get().getId());
    }

    @Test
    void getItemById_notFound_returnsEmpty() {
        when(consumerClient.getById("unknown")).thenReturn(Optional.empty());

        Optional<CryptoItem> result = queryService.getItemById("unknown");

        assertTrue(result.isEmpty());
    }

}
