package com.example.localcuisine.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "cuisines",
    indexes = {
        @Index(name = "idx_cuisines_region_id", columnList = "region_id"),
        @Index(name = "idx_cuisines_region_name", columnList = "region_id, cuisinename")
    }
)

public class Cuisine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuisinename")  
    private String cuisineName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false) 
    private Region region;

    public Cuisine() {}

    public Cuisine(String cuisineName, String description, Region region) {
        this.cuisineName = cuisineName;
        this.description = description;
        this.region = region;
    }

    public Long getId() { return id; }
    public String getCuisineName() { return cuisineName; }
    public void setCuisineName(String cuisineName) { this.cuisineName = cuisineName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}
