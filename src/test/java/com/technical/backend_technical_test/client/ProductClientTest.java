package com.technical.backend_technical_test.client;

import com.technical.backend_technical_test.model.Product;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductClientTest {

    private MockWebServer mockWebServer;
    private ProductClient productClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        productClient = new ProductClient(
            WebClient.builder(),
            mockWebServer.url("/").toString()
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void returnsSimilarIds() {
        mockWebServer.enqueue(new MockResponse()
            .setBody("[2, 3, 4]")
            .addHeader("Content-Type", "application/json"));

        List<Integer> ids = productClient.getSimilarIdsMono("1").block();

        assertThat(ids).containsExactly(2, 3, 4);
    }

    @Test
    void returnsEmptyListWhenSimilarIdsNotFound() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        List<Integer> ids = productClient.getSimilarIdsMono("999").block();

        assertThat(ids).isEmpty();
    }

    @Test
    void returnsProductDetail() {
        mockWebServer.enqueue(new MockResponse()
            .setBody("{\"id\":\"2\",\"name\":\"Dress\",\"price\":19.99,\"availability\":true}")
            .addHeader("Content-Type", "application/json"));

        Product result = productClient.getProductDetailMono(2).block();

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Dress");
    }

    @Test
    void returnsNullWhenProductNotFound() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        Product result = productClient.getProductDetailMono(999).block();

        assertThat(result).isNull();
    }
}