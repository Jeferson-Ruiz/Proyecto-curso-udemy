package com.jeferson.springcloud.msvc.items.models;

import com.jeferson.libs.msvc.commons.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Product productDto; 
    private int quantity;


    public Double getTotal(){
        return productDto.getPrice() * quantity;
    }

}