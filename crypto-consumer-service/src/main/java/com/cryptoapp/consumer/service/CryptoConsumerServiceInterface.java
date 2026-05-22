package com.cryptoapp.consumer.service;

import com.cryptoapp.consumer.model.CryptoItem;
import java.util.List;
import java.util.Optional;

public interface CryptoConsumerServiceInterface {

    List<CryptoItem> getAllItems();

    Optional<CryptoItem> getItemById(String id);

    void refreshData();

}
