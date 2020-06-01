package com.example.CarInfoService.repository;

import com.example.CarInfoService.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model findModelById(Long id);

    List<Model> findAll();
}
