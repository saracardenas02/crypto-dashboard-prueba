package com.cryptoapp.management.client;

import com.cryptoapp.management.model.CryptoItem;
import java.util.Optional;

public interface ConsumerClientInterface {
    Optional<CryptoItem> getById(String id);
}

