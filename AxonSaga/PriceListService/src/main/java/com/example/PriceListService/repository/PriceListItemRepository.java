package com.example.PriceListService.repository;

import com.example.PriceListService.domain.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {

    List<PriceListItem> findAllByPriceListId(Long id);

    @Query("SELECT p FROM PriceListItem p WHERE p.advertisementId = :advertisementId AND p.priceList.id = :priceListId")
    PriceListItem checkAdvertisementIdExists(@Param("priceListId") Long priceListId, @Param("advertisementId") Long advertisementId);

    List<PriceListItem> findAllByAdvertisementId(Long id);

    @Query("SELECT pItem FROM PriceListItem pItem WHERE pItem.advertisementId = ?1 AND ((pItem.priceList.validFrom <= ?2 AND pItem.priceList.validTo >= ?2) OR (pItem.priceList.validFrom > ?2 AND pItem.priceList.validTo < ?3) OR (pItem.priceList.validFrom <= ?3 AND pItem.priceList.validTo >= ?3))")
    List<PriceListItem> findPriceListItemForSpecificPeriod(@Param("adId") Long adId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
