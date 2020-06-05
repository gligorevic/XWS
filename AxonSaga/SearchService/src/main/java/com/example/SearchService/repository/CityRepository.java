package com.example.SearchService.repository;

import com.example.SearchService.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(value = "SELECT * FROM CITY AS c WHERE UPPER(c.name) LIKE CONCAT('%',UPPER(:city),'%') ORDER BY CASE WHEN UPPER(c.name) LIKE CONCAT(UPPER(:city),'%') THEN 1 WHEN UPPER(c.name) LIKE CONCAT('%', UPPER(:city)) THEN 2 ELSE 3 END LIMIT 6", nativeQuery = true)
    List<City> findCitiesLike(@Param("city") String city);

    City findByName(String cityName);
}
