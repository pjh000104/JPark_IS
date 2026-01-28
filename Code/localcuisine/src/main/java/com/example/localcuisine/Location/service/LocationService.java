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

    // public String findByName(String name) {
    //     String city = locationRepository.findByName(name);
    //     if (city == null) {
    //         throw new IllegalArgumentException("Location not found: " + name);
    //     }
    //     return city;
    // }

    public List<String> getAllRegions() {
        return locationRepository.findAll()
                .stream()
                .map(Region::getRegionName)
                .collect(Collectors.toList());
    }
}
