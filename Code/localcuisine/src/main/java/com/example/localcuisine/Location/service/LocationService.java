package com.example.localcuisine.Location.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.localcuisine.Location.domain.LocationRepository;
import com.example.localcuisine.entity.Region;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<String> getAllRegions() {
        return locationRepository.findAll()
                .stream()
                .map(Region::getRegionName)
                .collect(Collectors.toList());
    }

    public List<String> searchRegions(String keyword) {
        return locationRepository
                .findByRegionNameContainingIgnoreCase(keyword)
                .stream()
                .map(Region::getRegionName)
                .collect(Collectors.toList());
    }
}
