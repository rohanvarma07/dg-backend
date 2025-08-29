package com.example.backend.dg_backend.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodId;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "prod_price")
    private BigInteger prodPrice;

    @Column(name = "prod_quantity")
    private int prodQuantity;

    @Column(name = "prod_description", columnDefinition = "TEXT")
    private String prodDescription;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    public Product() {
    }

    public Product(String prodName, BigInteger prodPrice, int prodQuantity, String prodDescription, String imgUrl, Category category) {
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
        this.prodDescription = prodDescription;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    // Getters and Setters
    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public BigInteger getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(BigInteger prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return prodId == product.prodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "prodId=" + prodId +
                ", prodName='" + prodName + '\'' +
                ", prodPrice=" + prodPrice +
                ", prodQuantity=" + prodQuantity +
                ", prodDescription='" + prodDescription + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", category=" + (category != null ? category.getCategoryName() : "null") +
                '}';
    }
}