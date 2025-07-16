package com.cosmetics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithBrandDto {
    private String id;
    private String name;
    private Double price;
    private String category;
    private boolean inStock;

    private BrandDto brand;
}
