package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand add(String brandName) throws CustomException{
        if(brandRepository.findBrandByBrandName(brandName) == null)
            return brandRepository.save(new Brand(brandName));
        else
            throw  new CustomException("Brand already exists", HttpStatus.BAD_REQUEST);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandByName(String brandName) throws CustomException {
        if(brandRepository.findBrandByBrandName(brandName) == null)
            throw  new CustomException("Brand doesn't exists", HttpStatus.BAD_REQUEST);

        return brandRepository.findBrandByBrandName(brandName);
    }
}
