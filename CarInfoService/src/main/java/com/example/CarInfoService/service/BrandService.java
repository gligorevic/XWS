package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
