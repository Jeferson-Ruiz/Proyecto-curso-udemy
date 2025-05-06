package com.jeferson.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import com.jeferson.springcloud.msvc.items.models.ItemDto;

@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder client;

    public ItemServiceWebClient(Builder client) {
        this.client = client;
    }

    @Override
    public List<ItemDto> findByAll() {
        return this.client.build()
        .get()
        .uri("http://msvc-products")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(ItemDto.class)
        .collectList()
            .block();
    }

    @Override
    public Optional<ItemDto> findById(Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return Optional.ofNullable(client.build().get().uri("http://msvc-products/{id}",params)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(ItemDto.class)
        .block());
    }
}