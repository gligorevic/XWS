package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand add(String brandName) {

        if(brandRepository.findBrandByBrandName(brandName) == null){
            Brand brand = new Brand(brandName);
            brandRepository.save(brand);
            return brand;
        }

        return null;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandByName(String brandName) {
        if(brandRepository.findBrandByBrandName(brandName) == null)
            return null;

        return brandRepository.findBrandByBrandName(brandName);
    }
}
