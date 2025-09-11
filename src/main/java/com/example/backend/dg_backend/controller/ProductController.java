package com.example.backend.dg_backend.controller;

import com.example.backend.dg_backend.model.Product;
import com.example.backend.dg_backend.model.Category;
import com.example.backend.dg_backend.service.FileStorageService;
import com.example.backend.dg_backend.service.ProductService;
import com.example.backend.dg_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/products")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable int id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products/category/{categoryId}")
    public List<Product> findByCategory(@PathVariable int categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @PostMapping(value = "/products", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> createProduct(
            @RequestParam String prod_name,
            @RequestParam BigInteger prod_price,
            @RequestParam int prod_quantity,
            @RequestParam String prod_description,
            @RequestParam(value = "category_id", required = false) Integer categoryId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        String imgUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imgUrl = fileStorageService.storeFileWithProductName(imageFile, prod_name);
        }

        // Find category if provided
        Category category = null;
        if (categoryId != null) {
            Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
            if (categoryOpt.isPresent()) {
                category = categoryOpt.get();
            } else {
                return ResponseEntity.badRequest().build(); // Invalid category ID
            }
        }

        // Build new product object
        Product newProduct = new Product();
        newProduct.setProdName(prod_name);
        newProduct.setProdPrice(prod_price);
        newProduct.setProdQuantity(prod_quantity);
        newProduct.setProdDescription(prod_description);
        newProduct.setImgUrl(imgUrl);
        newProduct.setCategory(category);

        // Save to DB
        Product savedProduct = productService.save(newProduct);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,
            @RequestParam String prod_name,
            @RequestParam BigInteger prod_price,
            @RequestParam int prod_quantity,
            @RequestParam String prod_description,
            @RequestParam(value = "category_id", required = false) Integer categoryId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        String imgUrl = existingProduct.getImgUrl();
        if (imageFile != null && !imageFile.isEmpty()) {
            imgUrl = fileStorageService.storeFileWithProductName(imageFile, prod_name);
        }

        // Find category if provided
        Category category = null;
        if (categoryId != null) {
            Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
            if (categoryOpt.isPresent()) {
                category = categoryOpt.get();
            } else {
                return ResponseEntity.badRequest().build(); // Invalid category ID
            }
        }

        // Update product
        existingProduct.setProdName(prod_name);
        existingProduct.setProdPrice(prod_price);
        existingProduct.setProdQuantity(prod_quantity);
        existingProduct.setProdDescription(prod_description);
        existingProduct.setImgUrl(imgUrl);
        existingProduct.setCategory(category);

        Product updatedProduct = productService.save(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
