package com.jeferson.springcloud.msvc.products.controllers;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.products.enties.Product;
import com.jeferson.springcloud.msvc.products.services.ProductService;
import org.springframework.web.bind.annotation.PutMapping;

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
            // throw new IllegalStateException("Producto no encontrado");
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

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{idproduct}")
    public ResponseEntity<?> update(@PathVariable Long idproduct, @RequestBody Product product) {
        Optional<Product> optProduct = productService.findById(idproduct);
        if (optProduct.isPresent()) {
            Product productDb = optProduct.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            product.setCreateAt(product.getCreateAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idProduct}")
    public ResponseEntity<?> deleteById(@PathVariable Long idProduct) {
        Optional<Product> optProduct = productService.findById(idProduct);
        if (optProduct.isPresent()) {
            productService.delete(idProduct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}