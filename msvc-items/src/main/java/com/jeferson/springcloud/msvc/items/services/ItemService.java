package com.jeferson.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.springcloud.msvc.items.models.ItemDto;

public interface ItemService {

    List<ItemDto> findByAll();

    Optional<ItemDto> findById();
}
