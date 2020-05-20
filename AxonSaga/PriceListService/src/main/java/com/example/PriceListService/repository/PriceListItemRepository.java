package com.example.PriceListService.repository;

import com.example.PriceListService.domain.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {

}
