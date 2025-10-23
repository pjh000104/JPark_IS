package com.example.localcuisine.Cuisine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.localcuisine.Cuisine.domain.CuisineRepository;

@Service
public class CuisineService {
    private final CuisineRepository cuisineRepository;
    public CuisineService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }
    public List<String> getCuisinesByCity(String city) {
        return cuisineRepository.findByArea(city);
    }
}
