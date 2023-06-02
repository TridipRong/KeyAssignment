package com.key.assignment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.key.assignment.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
