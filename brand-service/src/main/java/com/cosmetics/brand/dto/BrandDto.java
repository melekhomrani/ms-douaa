package com.cosmetics.brand.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    private Long id;
    private String name;
    private String founder;
    private String country;
}