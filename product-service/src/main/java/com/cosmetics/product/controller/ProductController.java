package com.cosmetics.product.controller;


import com.cosmetics.dto.BrandDto;
import com.cosmetics.dto.ProductDto;
import com.cosmetics.dto.ProductWithBrandDto;
import com.cosmetics.product.feign.BrandClient;
import com.cosmetics.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final BrandClient brandClient;

    public ProductController(ProductService service, BrandClient brandClient) {
        this.service = service;
        this.brandClient = brandClient;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/with-brand")
    public List<ProductWithBrandDto> getProductsWithBrands() {
        return service.getProductsWithBrands();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable String id) {
        return service.getProductById(id);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        return service.createProduct(dto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable String id, @RequestBody ProductDto dto) {
        return service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
    }

    @GetMapping("/test-fallback")
    public BrandDto testFallback() {
        return brandClient.getBrand(123L);
    }
  
}