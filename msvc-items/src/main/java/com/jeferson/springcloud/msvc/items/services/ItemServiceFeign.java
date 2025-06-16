package com.jeferson.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.jeferson.springcloud.msvc.items.clients.ProductFeignClient;
import com.jeferson.springcloud.msvc.items.models.ItemDto;
import com.jeferson.springcloud.msvc.items.models.ProductDto;
import feign.FeignException.FeignClientException;

@Service
public class ItemServiceFeign implements ItemService {

    private final ProductFeignClient productFeignClient;

    public ItemServiceFeign(ProductFeignClient productFeignClient) {
        this.productFeignClient = productFeignClient;
    }

    @Override
    public List<ItemDto> findByAll() {
        return productFeignClient.listProducts()
                .stream()
                .map(product -> new ItemDto(product, new Random().nextInt(10) + 1))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemDto> findById(Long id) {

        try {
            ProductDto product = productFeignClient.details(id);
            return Optional.of(new ItemDto(product, new Random().nextInt(10) + 1));

        } catch (FeignClientException e) {
            return Optional.empty();
        }
    }

    @Override
    public ProductDto save(ProductDto product) {
        return productFeignClient.create(product);
    }

    @Override
    public ProductDto update(ProductDto product, Long id) {
        return productFeignClient.update(product, id);
    }

    @Override
    public void delete(Long id) {
        productFeignClient.delete(id);
    }
}