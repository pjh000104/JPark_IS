package com.example.localcuisine.Cuisine.domain;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CuisineRepository {
        private Map<String, List<String>> sampleMap = Map.of(
        "Tokyo", List.of("Sushi", "Ramen", "Tempura"),
        "Osaka", List.of("Takoyaki", "Okonomiyaki"),
        "Kyoto", List.of("Kaiseki", "Yudofu")
        );
    public List<String> findByArea (String city) {
        List<String> cuisines = sampleMap.get(city);
        if (cuisines != null) {
            return cuisines;
        } else {
            throw new IllegalArgumentException("City not found: " + city);
        }
    }
}
