package com.hbu.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbu.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
