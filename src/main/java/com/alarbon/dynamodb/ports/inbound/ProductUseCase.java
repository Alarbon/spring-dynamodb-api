package com.alarbon.dynamodb.ports.inbound;

import com.alarbon.dynamodb.domain.model.Product;
import java.util.List;

public interface ProductUseCase {
    Product create(Product product);
    Product getById(String id);
    List<Product> getAll();
    void delete(String id);
}