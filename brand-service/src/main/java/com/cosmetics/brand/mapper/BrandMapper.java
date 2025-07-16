package com.cosmetics.brand.mapper;

import com.cosmetics.dto.BrandDto;
import com.cosmetics.brand.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper {

    BrandMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BrandMapper.class);

    BrandDto toDto(Brand brand);
    Brand toEntity(BrandDto brandDto);

    List<BrandDto> toDtos(List<Brand> brands);
    List<Brand> toEntities(List<BrandDto> dtos);

    // Prevent nulls in DTO from overriding existing entity fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BrandDto brandDto, @MappingTarget Brand brand);
}
