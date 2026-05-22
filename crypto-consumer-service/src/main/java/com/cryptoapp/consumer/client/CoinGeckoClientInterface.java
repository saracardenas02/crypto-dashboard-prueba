package com.cryptoapp.consumer.client;

import com.cryptoapp.consumer.model.CryptoItem;
import java.util.List;

public interface CoinGeckoClientInterface {
    List<CryptoItem> fetchTopCoins(int limit);
}