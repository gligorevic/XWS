package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model findModelById(Long id);

    List<Model> findAll();

    Model findModelByModelName(String modelName);

    @Query(value = "SELECT * FROM MODEL AS m WHERE m.BRAND_ID = :brandId", nativeQuery = true)
    List<Model> findModelsByBrand(Long brandId);

    @Query(value = "SELECT DISTINCT ID FROM BODY_TYPE AS bt INNER JOIN (SELECT DISTINCT * FROM MODEL_BODY_TYPES AS mbt WHERE mbt.MODEL_ID = :modelId) AS bt2 ON bt.ID = bt2.BODY_TYPE_ID", nativeQuery = true)
    List<Long> findBodyTypesByModelId(Long modelId);

    @Query(value = "SELECT DISTINCT ID AS GEAR_SHIFT_TYPE FROM GEAR_SHIFT_TYPE AS gst INNER JOIN (SELECT DISTINCT * FROM MODEL_GEAR_SHIFT_TYPES AS mgst WHERE mgst.MODEL_ID = :modelId) AS gst2 ON gst.ID = gst2.GEAR_SHIFT_TYPE_ID", nativeQuery = true)
    List<Long> findGearShiftTypesByModelId(Long modelId);

    @Query(value = "SELECT DISTINCT ID AS FUEL_TYPE FROM FUEL_TYPE AS ft INNER JOIN (SELECT DISTINCT * FROM MODEL_FUEL_TYPES AS mft WHERE mft.MODEL_ID = :modelId) AS ft2 ON ft.ID = ft2.FUEL_TYPE_ID", nativeQuery = true)
    List<Long> findFuelTypesByModelId(Long modelId);
}
