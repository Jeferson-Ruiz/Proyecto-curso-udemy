package com.jeferson.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jeferson.springcloud.msvc.products.enties.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
