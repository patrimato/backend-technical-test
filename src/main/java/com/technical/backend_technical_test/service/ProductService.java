package com.technical.backend_technical_test.service;

import com.technical.backend_technical_test.client.ProductClient;
import com.technical.backend_technical_test.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<Product> getSimilarProducts(String productId) {
        return productClient.getSimilarIdsMono(productId)
            .flatMapMany(Flux::fromIterable)
            .flatMap(id -> productClient.getProductDetailMono(id)
                .onErrorResume(e -> Mono.empty()))
            .collectList()
            .block();
    }
}