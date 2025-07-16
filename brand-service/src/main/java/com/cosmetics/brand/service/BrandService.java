package com.cosmetics.brand.service;

import com.cosmetics.dto.BrandDto;
import com.cosmetics.kafka.BrandNotification;
import com.cosmetics.brand.model.Brand;
import com.cosmetics.brand.kafka.BrandProducer;
import com.cosmetics.brand.mapper.BrandMapper;
import com.cosmetics.brand.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository repository;
    private final BrandMapper mapper;
    private final BrandProducer brandProducer;

    public BrandService(BrandRepository repository, BrandMapper mapper,
            BrandProducer brandProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.brandProducer = brandProducer;
    }

    public List<BrandDto> getAllBrands() {
        return mapper.toDtos(repository.findAll());
    }

    public BrandDto getBrandById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    public BrandDto createBrand(BrandDto dto) {
        Brand saved = repository.save(mapper.toEntity(dto));

        String email = saved.getFounder()
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", ".") + "@cosmetics.com";
        brandProducer.sendBrandNotification(
                new BrandNotification(
                        email,
                        saved.getName(),
                        saved.getFounder(),
                        saved.getCountry()));
        return mapper.toDto(saved);
    }

    public BrandDto updateBrand(Long id, BrandDto dto) {
        Brand existing = repository.findById(id).orElseThrow();
        mapper.updateEntityFromDto(dto, existing); // Appel de la méthode MapStruct
        return mapper.toDto(repository.save(existing));
    }

    public void deleteBrand(Long id) {
        repository.deleteById(id);
    }
}