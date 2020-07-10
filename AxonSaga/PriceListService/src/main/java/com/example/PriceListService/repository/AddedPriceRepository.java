package com.example.PriceListService.repository;

import com.example.PriceListService.domain.AddedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddedPriceRepository extends JpaRepository<AddedPrice, Long> {

    Optional<AddedPrice> findById(Long id);

    @Query("SELECT ap FROM AddedPrice ap WHERE ap.userEmail = :email AND ap.paidState = 0")
    List<AddedPrice> getNotPaidAddedPrices(@Param("email") String email);

}
