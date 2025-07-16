package com.cosmetics.kafka;

public record BrandNotification(
    String email,
    String name,
    String founder,
    String country) {
}