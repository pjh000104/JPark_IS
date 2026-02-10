package com.example.localcuisine.Cuisine.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.cache.CacheService;
import com.example.localcuisine.entity.Cuisine;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineService(CacheService cacheService, CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
        this.cacheService = cacheService;
    }

    private CacheService cacheService;

    public List<String> getCuisinesByRegion(String regionName) {

        String cacheKey = "region:" + regionName + ":cuisineNames";

        // 1. Check cache
        Object cached = cacheService.get(cacheKey);
        if (cached != null) {
            System.out.println("cache hit");
            return (List<String>) cached;
        }
        System.out.println("cache miss");

        // 2. Cache miss → original logic
        List<Cuisine> cuisines =
                cuisineRepository.findByRegion_RegionName(regionName);

        List<String> cuisineNames = cuisines.stream()
                .map(Cuisine::getCuisineName)
                .collect(Collectors.toList());

        // 3. Store result in cache
        cacheService.put(cacheKey, cuisineNames, 3600);

        return cuisineNames;
    }


    public List<String> getCuisinesByCountry(String country) {
        List<Cuisine> cuisines = cuisineRepository.findByRegion_Country(country);
        return cuisines.stream()
                       .map(Cuisine::getCuisineName)
                       .collect(Collectors.toList());
    }

    public List<Cuisine> getFullCuisinesByRegion(String regionName) {
        return cuisineRepository.findByRegion_RegionName(regionName);
    }
}
