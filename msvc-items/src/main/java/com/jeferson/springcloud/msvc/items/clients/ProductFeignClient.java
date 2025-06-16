package com.jeferson.springcloud.msvc.items.clients;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.jeferson.springcloud.msvc.items.models.ProductDto;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping
    List<ProductDto> listProducts();

    @GetMapping("/{id}")
    ProductDto details(@PathVariable Long id);

    @PostMapping
    ProductDto create(@RequestBody ProductDto prodcut);

    @PutMapping("/{id}")
    ProductDto update(@RequestBody ProductDto productDto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);
}
