package com.technical.backend_technical_test.client;

import com.technical.backend_technical_test.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(
        WebClient.Builder webClientBuilder,
        @Value("${mock.base-url}") String baseUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<List<Integer>> getSimilarIdsMono(String productId) {
        return webClient.get()
            .uri("/product/{productId}/similarids", productId)
            .retrieve()
            .bodyToMono(Integer[].class)
            .map(ids -> ids != null ? List.of(ids) : List.<Integer>of())
            .onErrorReturn(List.of());
    }

    public Mono<Product> getProductDetailMono(Integer id) {
        return webClient.get()
            .uri("/product/{id}", id)
            .retrieve()
            .bodyToMono(Product.class)
            .onErrorResume(e -> Mono.empty());
    }
}