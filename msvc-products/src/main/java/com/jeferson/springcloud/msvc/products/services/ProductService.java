package com.jeferson.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.libs.msvc.commons.entities.Product;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product prodcut);

    void delete(Long idProduct);
}