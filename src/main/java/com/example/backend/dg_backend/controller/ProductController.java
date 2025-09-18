package com.example.backend.dg_backend.controller;

import com.example.backend.dg_backend.model.Product;
import com.example.backend.dg_backend.model.Category;
import com.example.backend.dg_backend.service.FileStorageService;
import com.example.backend.dg_backend.service.ProductService;
import com.example.backend.dg_backend.service.CategoryService;
import com.example.backend.dg_backend.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    public ResponseEntity<?> createProduct(
            @RequestParam @NotBlank(message = "Product name is required") String prod_name,
            @RequestParam @NotNull(message = "Product price is required") @Positive(message = "Price must be positive") BigInteger prod_price,
            @RequestParam @Positive(message = "Quantity must be positive") int prod_quantity,
            @RequestParam @NotBlank(message = "Product description is required") String prod_description,
            @RequestParam(value = "category_id", required = false) Integer categoryId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            // Validate required fields manually since @Valid doesn't work with @RequestParam
            if (prod_name == null || prod_name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Product name is required");
            }
            if (prod_price == null || prod_price.compareTo(BigInteger.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("Product price must be positive");
            }
            if (prod_quantity <= 0) {
                return ResponseEntity.badRequest().body("Product quantity must be positive");
            }
            if (prod_description == null || prod_description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Product description is required");
            }

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
                    return ResponseEntity.badRequest().body("Invalid category ID: " + categoryId);
                }
            }

            // Build new product object
            Product newProduct = new Product();
            newProduct.setProdName(prod_name.trim());
            newProduct.setProdPrice(prod_price);
            newProduct.setProdQuantity(prod_quantity);
            newProduct.setProdDescription(prod_description.trim());
            newProduct.setImgUrl(imgUrl);
            newProduct.setCategory(category);

            // Save to DB
            Product savedProduct = productService.save(newProduct);

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create product: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create product: " + e.getMessage());
        }
    }

    @PostMapping(value = "/products", consumes = "application/json")
    public ResponseEntity<?> createProductJson(@Valid @RequestBody ProductDTO productDTO) {
        try {
            System.out.println("[DEBUG] Received JSON product POST: " + productDTO);
            System.out.println("[DEBUG] Request processing started at: " + java.time.LocalDateTime.now());

            // Validate required fields
            if (productDTO.prod_name == null || productDTO.prod_name.trim().isEmpty()) {
                System.err.println("[ERROR] Validation failed: Product name is required");
                return ResponseEntity.badRequest().body("Product name is required");
            }
            if (productDTO.prod_price == null || productDTO.prod_price.compareTo(BigInteger.ZERO) <= 0) {
                System.err.println("[ERROR] Validation failed: Product price must be positive");
                return ResponseEntity.badRequest().body("Product price must be positive");
            }
            if (productDTO.prod_quantity <= 0) {
                System.err.println("[ERROR] Validation failed: Product quantity must be positive");
                return ResponseEntity.badRequest().body("Product quantity must be positive");
            }
            if (productDTO.prod_description == null || productDTO.prod_description.trim().isEmpty()) {
                System.err.println("[ERROR] Validation failed: Product description is required");
                return ResponseEntity.badRequest().body("Product description is required");
            }

            String prod_name = productDTO.prod_name.trim();
            BigInteger prod_price = productDTO.prod_price;
            int prod_quantity = productDTO.prod_quantity;
            String prod_description = productDTO.prod_description.trim();
            Integer categoryId = productDTO.category_id;
            String imgUrl = productDTO.img_url;

            // Find category if provided
            Category category = null;
            if (categoryId != null) {
                System.out.println("[DEBUG] Looking up category with ID: " + categoryId);
                Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
                if (categoryOpt.isPresent()) {
                    category = categoryOpt.get();
                    System.out.println("[DEBUG] Found category: " + category.getCategoryName());
                } else {
                    System.err.println("[ERROR] Invalid category ID: " + categoryId);
                    return ResponseEntity.badRequest().body("Invalid category ID: " + categoryId);
                }
            } else {
                System.out.println("[DEBUG] No category provided, creating product without category");
            }

            // Build new product object
            Product newProduct = new Product();
            newProduct.setProdName(prod_name);
            newProduct.setProdPrice(prod_price);
            newProduct.setProdQuantity(prod_quantity);
            newProduct.setProdDescription(prod_description);
            newProduct.setImgUrl(imgUrl);
            newProduct.setCategory(category);

            System.out.println("[DEBUG] Created product object: " + newProduct);
            System.out.println("[DEBUG] About to call productService.save()");

            // Save to DB
            Product savedProduct = productService.save(newProduct);

            System.out.println("[DEBUG] Product saved successfully with ID: " + savedProduct.getProdId());
            System.out.println("[DEBUG] Saved product details: " + savedProduct);
            System.out.println("[DEBUG] Request processing completed at: " + java.time.LocalDateTime.now());

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create product: " + e.getMessage());
            System.err.println("[ERROR] Exception type: " + e.getClass().getSimpleName());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create product: " + e.getMessage());
        }
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
