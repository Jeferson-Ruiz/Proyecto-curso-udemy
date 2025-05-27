package com.jeferson.springcloud.msvc.products.controllers;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.products.enties.Product;
import com.jeferson.springcloud.msvc.products.services.ProductService;

@RestController
public class ProductController {

    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> listProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) throws InterruptedException {

        if (id.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado");
        }

        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(1L);
        }

        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}