package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

    BodyType findBodyTypeByBodyTypeName(String bodyTypeName);

    BodyType findBodyTypeById(Long id);

}
