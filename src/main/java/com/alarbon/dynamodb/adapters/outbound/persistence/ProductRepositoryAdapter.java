package com.alarbon.dynamodb.adapters.outbound.persistence;

import com.alarbon.dynamodb.adapters.outbound.persistence.entity.ProductDynamoEntity;
import com.alarbon.dynamodb.domain.model.Product;
import com.alarbon.dynamodb.ports.outbound.ProductRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final DynamoDbTable<ProductDynamoEntity> productTable;

    public ProductRepositoryAdapter(DynamoDbTable<ProductDynamoEntity> productTable) {
        this.productTable = productTable;
    }

    @Override
    public Product save(Product product) {
        log.debug("Database operation: Saving entity with SKU {} to DynamoDB", product.getSku());

        ProductDynamoEntity entity = new ProductDynamoEntity();
        entity.setProductId(product.getProductId());
        entity.setName(product.getName());
        entity.setSku(product.getSku());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        entity.setStatus(product.getStatus());
        entity.setCreatedAt(product.getCreatedAt());

        productTable.putItem(entity);
        return product;
    }

    @Override
    public Optional<Product> findById(String id) {
        log.debug("Database operation: Finding entity by ID {} in DynamoDB", id);
        ProductDynamoEntity entity = productTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        return Optional.ofNullable(entity).map(this::mapToDomain);
    }

    @Override
    public List<Product> findAll() {
        log.debug("Database operation: Scanning all items from DynamoDB table");
        PageIterable<ProductDynamoEntity> pages = productTable.scan();
        return pages.items().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        log.debug("Database operation: Deleting entity by ID {} from DynamoDB", id);
        productTable.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }

    private ProductDynamoEntity mapToEntity(Product domain) {
        ProductDynamoEntity entity = new ProductDynamoEntity();
        entity.setProductId(domain.getProductId());
        entity.setName(domain.getName());
        entity.setSku(domain.getSku());
        entity.setPrice(domain.getPrice());
        entity.setStock(domain.getStock());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    private Product mapToDomain(ProductDynamoEntity entity) {
        return Product.builder()
                .productId(entity.getProductId())
                .name(entity.getName())
                .sku(entity.getSku())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}