package com.technical.backendtechnicaltest.service;

import com.technical.backendtechnicaltest.client.ProductClient;
import com.technical.backendtechnicaltest.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<Product> getSimilarProducts(String productId) {
        return productClient.getSimilarIds(productId)
            .stream()
            .map(productClient::getProductDetail)
            .toList();
    }
}