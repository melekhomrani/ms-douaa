package com.cosmetics.product.feign;

import com.cosmetics.dto.BrandDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "brand-service",
    configuration = FeignConfig.class
)
public interface BrandClient {

    @GetMapping("/api/brands/{id}")
    BrandDto getBrand(@PathVariable("id") Long id);
}
