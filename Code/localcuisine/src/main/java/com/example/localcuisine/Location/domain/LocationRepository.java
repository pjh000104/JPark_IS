package com.example.localcuisine.Location.domain;

import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {
    private String city = "samplecity";
    public String findByName(String city) {
        if(city == this.city) {
            return this.city;
        }
        else{
            return "no city";
        }
    }
}
