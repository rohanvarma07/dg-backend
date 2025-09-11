package com.example.backend.dg_backend.service;

import com.example.backend.dg_backend.model.Product;
import com.example.backend.dg_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByCategoryId(Integer categoryId) {
        if (categoryId == null) {
            return productRepository.findByCategoryIsNull();
        }
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    public List<Product> findProductsWithoutCategory() {
        return productRepository.findByCategoryIsNull();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}