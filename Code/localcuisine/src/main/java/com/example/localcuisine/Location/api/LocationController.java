package com.example.localcuisine.Location.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.localcuisine.Location.service.LocationService;
import java.util.List;

// location is needed to display location data later

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // returns all locations saved
    @GetMapping("/all")
    public ResponseEntity<String> getAllLocations() {
        // make it into list later
        String locations = locationService.findByName("test");
        return ResponseEntity.ok(locations);
    }
}


