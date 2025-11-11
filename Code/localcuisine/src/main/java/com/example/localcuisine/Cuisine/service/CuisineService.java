package com.example.localcuisine.Cuisine.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.entity.Cuisine;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }


    public List<String> getCuisinesByRegion(String regionName) {
        List<Cuisine> cuisines = cuisineRepository.findByRegion_RegionName(regionName);
        return cuisines.stream()
                       .map(Cuisine::getCuisineName) 
                       .collect(Collectors.toList());
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
