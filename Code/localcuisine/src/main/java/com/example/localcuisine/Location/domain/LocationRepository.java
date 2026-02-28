package com.example.localcuisine.Location.domain;

import com.example.localcuisine.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocationRepository extends JpaRepository<Region, Long> {
    List<Region> findByRegionNameContainingIgnoreCase(String name);
}
