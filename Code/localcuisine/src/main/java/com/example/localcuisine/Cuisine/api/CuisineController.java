package com.example.localcuisine.Cuisine.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.localcuisine.Cuisine.service.CuisineService;
import java.util.List;

@RestController
@RequestMapping("/api/cuisines")
public class CuisineController {

    private final CuisineService cuisineService;

    public CuisineController(CuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }

    @GetMapping("/byRegion/{regionName}")
    public List<String> getByRegion(@PathVariable String regionName) {
        return cuisineService.getCuisinesByRegion(regionName);
    }
    @GetMapping("/{cuisineName}")
    public ResponseEntity<CuisineResponse> getByCuisineName(@PathVariable String cuisineName) {
        return ResponseEntity.ok(cuisineService.getCuisineByName(cuisineName));
    }
}
