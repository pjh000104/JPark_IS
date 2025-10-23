package com.example.localcuisine.Location.service;

import org.springframework.stereotype.Service;
import com.example.localcuisine.Location.domain.LocationRepository;
@Service
public class LocationService {
    private final LocationRepository locationRepository;
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public String findByName(String name) {
        String city = locationRepository.findByName(name);
        if (city == null) {
            throw new IllegalArgumentException("Location not found: " + name);
        }
        return city;
    }

}
