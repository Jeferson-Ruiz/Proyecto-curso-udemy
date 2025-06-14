package com.jeferson.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import com.jeferson.springcloud.msvc.items.models.ItemDto;
import com.jeferson.springcloud.msvc.items.models.ProductDto;

@Primary
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
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(ProductDto.class)
            .map(product -> new ItemDto(product, new Random().nextInt(10) + 1))
        .collectList()
            .block();
    }

    @Override
    public Optional<ItemDto> findById(Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        // try{
            return Optional.ofNullable(client.build().get().uri("/{id}",params)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ProductDto.class)
            .map(product -> new ItemDto(product, new Random().nextInt(10) + 1))
            .block());

        // }catch(WebClientRequestException e){
        //     return Optional.empty();
        // }
    }
}