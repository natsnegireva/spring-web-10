package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEventHttpMessageReader;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient prductServiceWebClient;
    @Value("${integrations.core-service.url}")

    public Optional<ProductDto> findById(Long id) {
        ProductDto productDto = prductServiceWebClient.get()
                .uri("/api/v1/products" + id)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ServerDontWorkingException("Product-MS not working")))
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ResourceNotFoundException("Product not found, id = " + id)))
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);
    }

// private final RestTemplate restTemplate;
// @Value("${integrations.core-service.url}")
// private String productServiceUrl;
// public Optional<ProductDto> findById(Long id) {
//        ProductDto productDto = restTemplate.getForObject(productServiceUrl + "/api/v1/products/" + id, ProductDto.class);
//        return Optional.ofNullable(productDto);
// }
}
