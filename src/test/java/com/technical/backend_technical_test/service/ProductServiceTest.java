package com.technical.backend_technical_test.service;

import com.technical.backend_technical_test.client.ProductClient;
import com.technical.backend_technical_test.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ProductService productService;

    @Test
    void returnsSimilarProductsWithDetails() {
        when(productClient.getSimilarIds("1")).thenReturn(List.of(2, 3));
        when(productClient.getProductDetail(2)).thenReturn(new Product("2", "Dress", 19.99, true));
        when(productClient.getProductDetail(3)).thenReturn(new Product("3", "Blazer", 29.99, false));

        List<Product> result = productService.getSimilarProducts("1");

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::name).containsExactlyInAnyOrder("Dress", "Blazer");
    }

    @Test
    void ignoresNullProductsWhenDetailNotFound() {
        when(productClient.getSimilarIds("1")).thenReturn(List.of(2, 999));
        when(productClient.getProductDetail(2)).thenReturn(new Product("2", "Dress", 19.99, true));
        when(productClient.getProductDetail(999)).thenReturn(null);

        List<Product> result = productService.getSimilarProducts("1");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Dress");
    }

    @Test
    void returnsEmptyListWhenNoSimilarIds() {
        when(productClient.getSimilarIds("999")).thenReturn(List.of());

        List<Product> result = productService.getSimilarProducts("999");

        assertThat(result).isEmpty();
    }
}