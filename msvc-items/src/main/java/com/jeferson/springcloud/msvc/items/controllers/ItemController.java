package com.jeferson.springcloud.msvc.items.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.items.models.ItemDto;
import com.jeferson.springcloud.msvc.items.services.ItemService;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDto> list (){
        return itemService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id){
        Optional<ItemDto> optItem  = itemService.findById(id);
        if (optItem.isPresent()) {
            return ResponseEntity.ok(optItem.get());
        }
        return ResponseEntity.status(404)
            .body(Collections.singletonMap(
                "message","No existe el producto en el micorservicio msvc-products"));    
    }
}