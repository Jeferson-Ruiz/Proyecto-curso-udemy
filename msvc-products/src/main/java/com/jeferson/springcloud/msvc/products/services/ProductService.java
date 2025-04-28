package com.jeferson.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import com.jeferson.springcloud.msvc.products.enties.Product;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

}