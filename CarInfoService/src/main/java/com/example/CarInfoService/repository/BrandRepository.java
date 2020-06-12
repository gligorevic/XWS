package com.example.CarInfoService.repository;

import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findBrandByBrandName(String brandName);

    Brand findBrandById(Long id);


}
