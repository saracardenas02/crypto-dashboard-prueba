package com.cryptoapp.query.client;

import com.cryptoapp.query.model.CryptoItem;
import java.util.List;
import java.util.Optional;

public interface ConsumerClientInterface {

    List<CryptoItem> getAll();
    Optional<CryptoItem> getById(String id);

}
