package com.example.localcuisine.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")  
    private String regionName;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuisine> cuisines;

    public Region() {}

    public Region(String regionName, String country) {
        this.regionName = regionName;
        this.country = country;
    }

    public Long getId() { return id; }
    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public List<Cuisine> getCuisines() { return cuisines; }
    public void setCuisines(List<Cuisine> cuisines) { this.cuisines = cuisines; }
}
