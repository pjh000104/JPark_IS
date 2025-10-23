package com.example.localcuisine.Cuisine.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.localcuisine.Cuisine.service.CuisineService;
import java.util.List;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {
    private final CuisineService cuisineService;
    public CuisineController(CuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }

    @GetMapping("{area}")
    public ResponseEntity<List<String>> getAllLocations(@PathVariable String area) {
            
            List<String> cuisines = cuisineService.getCuisinesByCity(area);
            return ResponseEntity.ok(cuisines);
    }
}
