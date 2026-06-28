package com.technical.backendtechnicaltest.controller;

import com.technical.backendtechnicaltest.model.Product;
import com.technical.backendtechnicaltest.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{productId}/similar")
    public List<Product> getSimilarProducts(@PathVariable String productId) {
        return productService.getSimilarProducts(productId);
    }
}