package com.cryptoapp.query.client;
import com.cryptoapp.query.model.CryptoItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    public List<CryptoItem> getAll() {
        try {
            CryptoItem[] items = restTemplate.getForObject(
                    consumerServiceUrl, CryptoItem[].class);
            if (items == null) return Collections.emptyList();
            return Arrays.asList(items);
        } catch (Exception e) {
            System.err.println("[QueryService] Error calling consumer: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<CryptoItem> getById(String id) {
        try {
            CryptoItem item = restTemplate.getForObject(
                    consumerServiceUrl + "/" + id, CryptoItem.class);
            return Optional.ofNullable(item);
        } catch (HttpClientErrorException.NotFound e) {
            // El consumer devolvio 404: el id no existe
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("[QueryService] Error calling consumer for id "
                    + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }

}

