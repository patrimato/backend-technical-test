package com.technical.backend_technical_test.controller;

import com.technical.backend_technical_test.model.Product;
import com.technical.backend_technical_test.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void returnsSimilarProducts() throws Exception {
        List<Product> products = List.of(
            new Product("2", "Dress", 19.99, true),
            new Product("3", "Blazer", 29.99, false)
        );
        when(productService.getSimilarProducts("1")).thenReturn(products);

        mockMvc.perform(get("/product/1/similar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("2"))
            .andExpect(jsonPath("$[0].name").value("Dress"))
            .andExpect(jsonPath("$[1].id").value("3"));
    }

    @Test
    void returns404WhenNoSimilarProductsFound() throws Exception {
        when(productService.getSimilarProducts("999")).thenReturn(List.of());

        mockMvc.perform(get("/product/999/similar"))
            .andExpect(status().isNotFound());
    }
}