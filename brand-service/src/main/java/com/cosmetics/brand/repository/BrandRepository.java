package com.cosmetics.brand.repository;

import com.cosmetics.brand.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}