package com.cryptoapp.management.client;

import com.cryptoapp.management.model.CryptoItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Component
public class ConsumerClient implements ConsumerClientInterface {

    private final RestTemplate restTemplate;

    @Value("${consumer.service.url}")
    private String consumerServiceUrl;

    public ConsumerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<CryptoItem> getById(String id) {
        try {
            CryptoItem item = restTemplate.getForObject(
                    consumerServiceUrl + "/" + id, CryptoItem.class);
            return Optional.ofNullable(item);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("[ManagementService] Error calling consumer: " + e.getMessage());
            return Optional.empty();
        }
    }

}

