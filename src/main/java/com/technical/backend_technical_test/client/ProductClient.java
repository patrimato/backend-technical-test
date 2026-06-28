package com.technical.backend_technical_test.client;

import com.technical.backend_technical_test.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:3001";

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Integer> getSimilarIds(String productId) {
        Integer[] ids = restTemplate.getForObject(
            BASE_URL + "/product/" + productId + "/similarids",
            Integer[].class
        );
        return ids != null ? Arrays.asList(ids) : List.of();
    }

    public Product getProductDetail(Integer id) {
        return restTemplate.getForObject(
            BASE_URL + "/product/" + id,
            Product.class
        );
    }
}