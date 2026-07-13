package com.alarbon.dynamodb.domain.service;

import com.alarbon.dynamodb.domain.exception.ProductNotFoundException;
import com.alarbon.dynamodb.domain.model.Product;
import com.alarbon.dynamodb.ports.inbound.ProductUseCase;
import com.alarbon.dynamodb.ports.outbound.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Product create(Product product) {
        log.info("Processing request to create product with SKU: {}", product.getSku());

        Product productToSave = product.toBuilder()
                .productId(UUID.randomUUID().toString())
                .status("ACTIVE")
                .createdAt(Instant.now())
                .build();

        return productRepositoryPort.save(productToSave);
    }

    @Override
    public Product getById(String id) {
        log.info("Processing request to find product by ID: {}", id);
        return productRepositoryPort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> getAll() {
        log.info("Processing request to retrieve all products");
        return productRepositoryPort.findAll();
    }

    @Override
    public void delete(String id) {
        log.info("Processing request to delete product with ID: {}", id);
        this.getById(id);
        productRepositoryPort.deleteById(id);
    }
}