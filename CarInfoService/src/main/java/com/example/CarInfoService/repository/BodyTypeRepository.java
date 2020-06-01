package com.example.CarInfoService.repository;

import com.example.CarInfoService.domain.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

    BodyType findBodyTypeByBodyTypeName(String bodyTypeName);

}
