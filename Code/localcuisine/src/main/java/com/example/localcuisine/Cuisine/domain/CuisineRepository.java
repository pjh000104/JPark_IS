package com.example.localcuisine.Cuisine.domain;

import com.example.localcuisine.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
    List<Cuisine> findByRegion_RegionName(String regionName);
    List<Cuisine> findByRegion_Country(String country);
    Optional<Cuisine> findByCuisineNameIgnoreCase(String cuisineName);
}

