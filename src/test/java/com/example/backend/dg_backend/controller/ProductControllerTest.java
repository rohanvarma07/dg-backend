package com.example.backend.dg_backend.controller;

import com.example.backend.dg_backend.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testInsertProductWithoutCategoryOrImage() throws Exception {
        mockMvc.perform(multipart("/api/products")
                .param("prod_name", "Test Product")
                .param("prod_price", "1000")
                .param("prod_quantity", "10")
                .param("prod_description", "A test product")
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.prodName").value("Test Product"));
    }

    @Test
    void testInsertProductWithImage() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "test-image.png", MediaType.IMAGE_PNG_VALUE, "dummy image content".getBytes()
        );
        mockMvc.perform(multipart("/api/products")
                .file(imageFile)
                .param("prod_name", "Test Product With Image")
                .param("prod_price", "2000")
                .param("prod_quantity", "5")
                .param("prod_description", "Product with image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.prodName").value("Test Product With Image"))
        .andExpect(jsonPath("$.imgUrl").isNotEmpty());
    }
}
