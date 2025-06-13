package com.jeferson.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.springcloud.msvc.products.enties.Product;
import com.jeferson.springcloud.msvc.products.repositories.ProductRepository;

@Service
public class ProductsServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final Environment environment;

    public ProductsServiceImpl(ProductRepository productRepository, Environment environment) {
        this.environment = environment;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return ((List<Product>)productRepository.findAll())
            .stream().map(product -> {
                product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
                return product;
            }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id){
        return productRepository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        });
    }

    @Override
    @Transactional
    public Product save(Product prodcut) {
        return this.productRepository.save(prodcut);
    }

    @Override
    @Transactional
    public void delete(Long idProduct) {
        productRepository.deleteById(idProduct);
    }
}