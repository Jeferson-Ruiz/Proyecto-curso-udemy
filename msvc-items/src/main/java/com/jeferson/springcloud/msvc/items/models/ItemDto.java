package com.jeferson.springcloud.msvc.items.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private ProductDto productDto; 
    private int quantity;


    public Double getTotal(){
        return productDto.getPrice() * quantity;
    }

}