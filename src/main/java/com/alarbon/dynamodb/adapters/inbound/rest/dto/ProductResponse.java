package com.alarbon.dynamodb.adapters.inbound.rest.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ProductResponse {
    private String productId;
    private String name;
    private String sku;
    private Double price;
    private Integer stock;
    private String status;
    private Instant createdAt;
}