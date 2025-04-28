package com.jeferson.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.springcloud.msvc.products.enties.Product;
import com.jeferson.springcloud.msvc.products.repositories.ProductRepository;

@Service
public class ProductsServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return (List<Product>)productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }
}