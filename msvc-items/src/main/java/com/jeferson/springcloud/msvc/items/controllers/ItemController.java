package com.jeferson.springcloud.msvc.items.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.items.models.ItemDto;
import com.jeferson.springcloud.msvc.items.models.ProductDto;
import com.jeferson.springcloud.msvc.items.services.ItemService;

@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;
    private final CircuitBreakerFactory cBreakerFactory;

    public ItemController(ItemService itemService, CircuitBreakerFactory cBreakerFactory){
        this.itemService = itemService;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping
    public List<ItemDto> list (){
        return itemService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) throws InterruptedException{

        Optional<ItemDto> optItem = cBreakerFactory.create("items").run( () -> itemService.findById(id), e ->{
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            ProductDto product = new ProductDto();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("Camara Sony");
            product.setPrice(1250.00);
            return Optional.of(new ItemDto(product, 5));
        });

        if (optItem.isPresent()) {
            return ResponseEntity.ok(optItem.get());
        }
        return ResponseEntity.status(404)
            .body(Collections.singletonMap(
                "message","No existe el producto en el micorservicio msvc-products"));    
    }
}