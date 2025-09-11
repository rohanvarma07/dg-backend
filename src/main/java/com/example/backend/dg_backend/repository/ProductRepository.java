package com.example.backend.dg_backend.repository;

import com.example.backend.dg_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Find products by category ID
    List<Product> findByCategoryCategoryId(Integer categoryId);

    // Find products without a category (category is null)
    List<Product> findByCategoryIsNull();
}
