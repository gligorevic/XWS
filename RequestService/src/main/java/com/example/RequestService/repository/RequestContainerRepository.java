package com.example.RequestService.repository;

import com.example.RequestService.domain.RequestContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestContainerRepository extends JpaRepository<RequestContainer, Long> {

}
