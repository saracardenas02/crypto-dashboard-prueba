package com.cryptoapp.query.service;

import com.cryptoapp.query.model.CryptoItem;
import java.util.List;
import java.util.Optional;

public interface CryptoQueryServiceInterface {

    List<CryptoItem> getAllItems();

    Optional<CryptoItem> getItemById(String id);

}

