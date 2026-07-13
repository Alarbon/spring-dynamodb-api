package com.alarbon.dynamodb.adapters.outbound.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.time.Instant;

@Setter
@DynamoDbBean
public class ProductDynamoEntity {

    private String productId;
    @Getter
    private String name;
    @Getter
    private String sku;
    @Getter
    private Double price;
    @Getter
    private Integer stock;
    @Getter
    private String status;
    @Getter
    private Instant createdAt;

    public ProductDynamoEntity() {
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("productId")
    public String getProductId() {
        return productId;
    }

}