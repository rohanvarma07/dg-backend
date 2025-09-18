package com.example.backend.dg_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigInteger;

public class ProductDTO {
    @NotBlank(message = "Product name is required")
    public String prod_name;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    public BigInteger prod_price;

    @Positive(message = "Product quantity must be positive")
    public int prod_quantity;

    @NotBlank(message = "Product description is required")
    public String prod_description;

    public Integer category_id;

    public String img_url;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "prod_name='" + prod_name + '\'' +
                ", prod_price=" + prod_price +
                ", prod_quantity=" + prod_quantity +
                ", prod_description='" + prod_description + '\'' +
                ", category_id=" + category_id +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
