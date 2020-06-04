package com.example.CarInfoService.repository;

import com.example.CarInfoService.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findBrandByBrandName(String brandName);

}
