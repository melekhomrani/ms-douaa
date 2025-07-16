package com.cosmetics.kafka;

public record ProductNotification(
    String email,
    String name,
    String brandId,
    Double price,
    String category,
    boolean inStock) {

}
