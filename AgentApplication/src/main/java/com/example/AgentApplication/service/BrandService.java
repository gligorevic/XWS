package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Brand;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandByName(String brandName) throws CustomException {
        if(brandRepository.findBrandByBrandName(brandName) == null)
            throw  new CustomException("Brand doesn't exists", HttpStatus.BAD_REQUEST);

        return brandRepository.findBrandByBrandName(brandName);
    }
}
