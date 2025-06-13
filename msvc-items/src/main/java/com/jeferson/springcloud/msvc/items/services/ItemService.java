package com.jeferson.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.springcloud.msvc.items.models.ItemDto;
import com.jeferson.springcloud.msvc.items.models.ProductDto;

public interface ItemService {

    List<ItemDto> findByAll();

    Optional<ItemDto> findById(Long id);

    ProductDto save(ProductDto product);

    ProductDto update(ProductDto product, Long id);

    void delete(Long id);
}
