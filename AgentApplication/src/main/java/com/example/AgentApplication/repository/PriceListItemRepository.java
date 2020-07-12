package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {

    List<PriceListItem> findAll();

    List<PriceListItem> findAllByPriceListId(Long id);

    @Query("SELECT p FROM PriceListItem p WHERE p.advertisementId = :advertisementId AND p.priceList.id = :priceListId")
    PriceListItem checkAdvertisementIdExists(@Param("priceListId") Long priceListId, @Param("advertisementId") Long advertisementId);
}
