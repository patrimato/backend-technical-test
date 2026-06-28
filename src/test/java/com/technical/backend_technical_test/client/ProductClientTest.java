package com.technical.backend_technical_test.client;

import com.technical.backend_technical_test.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductClient productClient;

    @Test
    void returnsSimilarIds() {
        when(restTemplate.getForObject(
            "http://localhost:3001/product/1/similarids",
            Integer[].class
        )).thenReturn(new Integer[]{2, 3, 4});

        List<Integer> ids = productClient.getSimilarIds("1");

        assertThat(ids).containsExactly(2, 3, 4);
    }

    @Test
    void returnsEmptyListWhenSimilarIdsNotFound() {
        when(restTemplate.getForObject(
            "http://localhost:3001/product/999/similarids",
            Integer[].class
        )).thenThrow(HttpClientErrorException.NotFound.class);

        List<Integer> ids = productClient.getSimilarIds("999");

        assertThat(ids).isEmpty();
    }

    @Test
    void returnsProductDetail() {
        Product product = new Product("2", "Dress", 19.99, true);
        when(restTemplate.getForObject(
            "http://localhost:3001/product/2",
            Product.class
        )).thenReturn(product);

        Product result = productClient.getProductDetail(2);

        assertThat(result.name()).isEqualTo("Dress");
    }

    @Test
    void returnsNullWhenProductNotFound() {
        when(restTemplate.getForObject(
            "http://localhost:3001/product/999",
            Product.class
        )).thenThrow(HttpClientErrorException.NotFound.class);

        Product result = productClient.getProductDetail(999);

        assertThat(result).isNull();
    }
}