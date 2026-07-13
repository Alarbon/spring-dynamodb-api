package com.alarbon.dynamodb.adapters.inbound.rest;

import com.alarbon.dynamodb.adapters.inbound.rest.dto.CreateProductRequest;
import com.alarbon.dynamodb.adapters.inbound.rest.dto.ProductResponse;
import com.alarbon.dynamodb.domain.model.Product;
import com.alarbon.dynamodb.ports.inbound.ProductUseCase;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        log.info("Received HTTP POST request to create product: {}", request.getSku());

        Product productDomain = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        Product created = productUseCase.create(productDomain);
        return new ResponseEntity<>(mapToResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String id) {
        log.info("Received HTTP GET request for product ID: {}", id);
        Product product = productUseCase.getById(id);
        return ResponseEntity.ok(mapToResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Received HTTP GET request to list all products");
        List<ProductResponse> responses = productUseCase.getAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        log.info("Received HTTP DELETE request for product ID: {}", id);
        productUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .sku(product.getSku())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }
}