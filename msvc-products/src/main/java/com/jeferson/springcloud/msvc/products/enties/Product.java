package com.jeferson.springcloud.msvc.products.enties;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre", length = 20, nullable = false)
    private String name;

    @Column(name = "precio", nullable = false)
    private Double price;

    @Column(name = "creado_en", nullable = false)
    private LocalDate createAt;
}
