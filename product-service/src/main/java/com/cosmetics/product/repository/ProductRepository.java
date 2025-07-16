package com.cosmetics.product.repository;

import com.cosmetics.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
    Product findByBrandId(String brandId);
    boolean existsByName(String name);
    boolean existsByBrandId(String brandId);
}