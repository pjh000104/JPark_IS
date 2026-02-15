package com.example.localcuisine.Cuisine.api;

import com.example.localcuisine.entity.Cuisine;

public class CuisineResponse {

    private Long id;
    private String cuisineName;
    private String description;
    private String regionName;
    private String country;

    public CuisineResponse(Long id, String cuisineName, String description,
                           String regionName, String country) {
        this.id = id;
        this.cuisineName = cuisineName;
        this.description = description;
        this.regionName = regionName;
        this.country = country;
    }

    public static CuisineResponse from(Cuisine cuisine) {
        return new CuisineResponse(
                cuisine.getId(),
                cuisine.getCuisineName(),
                cuisine.getDescription(),
                cuisine.getRegion().getRegionName(),
                cuisine.getRegion().getCountry()
        );
    }

    public Long getId() {
        return id;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public String getDescription() {
        return description;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCountry() {
        return country;
    }
}
