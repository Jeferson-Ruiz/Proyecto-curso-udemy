package com.jeferson.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.libs.msvc.commons.entities.Product;
import com.jeferson.springcloud.msvc.items.models.ItemDto;

public interface ItemService {

    List<ItemDto> findByAll();

    Optional<ItemDto> findById(Long id);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);
}
