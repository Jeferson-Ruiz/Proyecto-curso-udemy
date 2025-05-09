package com.jeferson.springcloud.msvc.items.clients;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jeferson.springcloud.msvc.items.models.ProductDto;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping
    List<ProductDto> listProducts();  

    @GetMapping("/{id}")
    ProductDto details(@PathVariable Long id);
}
