package com.cosmetics.product.service;

import com.cosmetics.dto.BrandDto;
import com.cosmetics.dto.ProductDto;
import com.cosmetics.dto.ProductWithBrandDto;
import com.cosmetics.kafka.ProductNotification;
import com.cosmetics.product.feign.BrandClient;
import com.cosmetics.product.kafka.ProductProducer;
import com.cosmetics.product.mapper.ProductMapper;
import com.cosmetics.product.model.Product;
import com.cosmetics.product.repository.ProductRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final BrandClient brandClient;
    private final ProductProducer productProducer;

    public ProductService(ProductRepository repository, ProductMapper mapper, BrandClient brandClient,
            ProductProducer productProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.brandClient = brandClient;
        this.productProducer = productProducer;
    }

    @CircuitBreaker(name = "brandClient", fallbackMethod = "brandFallback")
    public BrandDto getBrandWithCircuitBreaker(Long id) {
        return brandClient.getBrand(id);
    }

    // Fallback method called when circuit breaker is OPEN or call fails
    public BrandDto brandFallback(Long id, Throwable t) {
        System.err.println(" Circuit breaker fallback triggered for brand ID: " + id + ", reason: " + t.getMessage());
        return new BrandDto(id, "Unavailable Brand", "N/A", "Unknown");
    }

    public List<ProductDto> getAllProducts() {
        return mapper.toDtos(repository.findAll());
    }

    public List<ProductWithBrandDto> getProductsWithBrands() {
        List<Product> products = repository.findAll();

        return products.stream().map(product -> {
            BrandDto brand = getBrandWithCircuitBreaker(Long.valueOf(product.getBrandId()));

            ProductWithBrandDto dto = new ProductWithBrandDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setCategory(product.getCategory());
            dto.setInStock(product.isInStock());
            dto.setBrand(brand);

            return dto;
        }).toList();
    }

    public ProductDto getProductById(String id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public ProductDto createProduct(ProductDto dto) {

        BrandDto brand = getBrandWithCircuitBreaker(Long.valueOf(dto.getBrandId()));

        if (brand == null) {
            throw new IllegalArgumentException("Brand with ID " + dto.getBrandId() +
                    " not found. Please ensure the brand exists before creating a product.");
        }

        if (repository.findByName(dto.getName()) != null) {
            throw new IllegalArgumentException("Product with name '" + dto.getName() + "' already exists.");
        }

        Product saved = repository.save(mapper.toEntity(dto));

        String email = brand.getFounder()
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", ".") + "@cosmetics.com";

        productProducer.sendProductNotification(
                new ProductNotification(
                        email,
                        saved.getName(),
                        brand.getFounder(),
                        saved.getPrice(),
                        saved.getCategory(),
                        saved.isInStock()
                )
        );

        return mapper.toDto(saved);
    }

    public ProductDto updateProduct(String id, ProductDto dto) {
        Product existing = repository.findById(id).orElseThrow();

        if (repository.findByName(dto.getName()) != null) {
            throw new IllegalArgumentException("Product with name '" + dto.getName() + "' already exists.");
        }

        mapper.updateEntityFromDto(dto, existing);
        return mapper.toDto(repository.save(existing));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
    
}