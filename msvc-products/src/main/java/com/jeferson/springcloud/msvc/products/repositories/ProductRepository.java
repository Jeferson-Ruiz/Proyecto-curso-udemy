package com.jeferson.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jeferson.libs.msvc.commons.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
