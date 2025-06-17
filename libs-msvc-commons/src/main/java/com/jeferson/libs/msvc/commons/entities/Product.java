package com.jeferson.libs.msvc.commons.entities;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


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

    @Transient
    private int port;

    public Product(Long id, String name, Double price, LocalDate createAt, int port) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createAt = createAt;
        this.port = port;
    }

    public Product(){
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Double getPrice() {
        return price;
    }


    public LocalDate getCreateAt() {
        return createAt;
    }


    public int getPort() {
        return port;
    }
}
