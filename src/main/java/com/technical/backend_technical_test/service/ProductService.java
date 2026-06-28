package com.technical.backend_technical_test.service;

import com.technical.backend_technical_test.client.ProductClient;
import com.technical.backend_technical_test.model.Product;
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