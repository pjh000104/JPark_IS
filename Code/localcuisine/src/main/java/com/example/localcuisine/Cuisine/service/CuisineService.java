package com.example.localcuisine.Cuisine.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.localcuisine.Cuisine.api.CuisineResponse;
import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.cache.CacheService;
import com.example.localcuisine.entity.Cuisine;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;
    private final ObjectMapper objectMapper;
    public CuisineService(CacheService cacheService, CuisineRepository cuisineRepository, ObjectMapper objectMapper) {
        this.cuisineRepository = cuisineRepository;
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
    }

    private CacheService cacheService;

    public List<String> getCuisinesByRegion(String regionName) {

        String cacheKey = "region:" + regionName + ":cuisines";

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

    public CuisineResponse getCuisineByName(String cuisineName) {

        String cacheKey = "cuisine:name:" + cuisineName.toLowerCase();

        // 1. Check cache
        Object cached = cacheService.get(cacheKey);
        if (cached != null) {
            CuisineResponse response =
                objectMapper.convertValue(cached, CuisineResponse.class);
        System.out.println("cache hit"); 
            return response;
        }

        System.out.println("cache miss");

        // 2. Cache miss → original logic
        Cuisine cuisine = cuisineRepository
                .findByCuisineNameIgnoreCase(cuisineName)
                .orElseThrow(() -> new RuntimeException("Cuisine not found"));

        CuisineResponse response = CuisineResponse.from(cuisine);

        // 3. Store result in cache
        cacheService.put(cacheKey, response, 3600);

        return response;
    }
}
