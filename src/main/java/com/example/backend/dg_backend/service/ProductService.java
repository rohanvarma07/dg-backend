package com.example.backend.dg_backend.service;

import com.example.backend.dg_backend.model.Product;
import com.example.backend.dg_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Product> findByCategoryId(Integer categoryId) {
        if (categoryId == null) {
            return productRepository.findByCategoryIsNull();
        }
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Product> findProductsWithoutCategory() {
        return productRepository.findByCategoryIsNull();
    }

    @Transactional
    public Product save(Product product) {
        try {
            System.out.println("[DEBUG] ProductService.save() - Attempting to save product: " + product);
            Product savedProduct = productRepository.save(product);
            System.out.println("[DEBUG] ProductService.save() - Product saved successfully with ID: " + savedProduct.getProdId());
            return savedProduct;
        } catch (Exception e) {
            System.err.println("[ERROR] ProductService.save() - Failed to save product: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteById(int id) {
        try {
            System.out.println("[DEBUG] ProductService.deleteById() - Attempting to delete product with ID: " + id);
            productRepository.deleteById(id);
            System.out.println("[DEBUG] ProductService.deleteById() - Product deleted successfully");
        } catch (Exception e) {
            System.err.println("[ERROR] ProductService.deleteById() - Failed to delete product: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }
}