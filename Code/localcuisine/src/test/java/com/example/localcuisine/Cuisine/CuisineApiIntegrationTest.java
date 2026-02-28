package com.example.localcuisine.Cuisine;

import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.entity.Cuisine;
import com.example.localcuisine.entity.Region;
import com.example.localcuisine.Location.domain.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CuisineApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Region tokyoRegion;

    @BeforeEach
    void setup() {
        tokyoRegion = new Region("Tokyo", "Japan");
        locationRepository.save(tokyoRegion);
        locationRepository.flush();

        Cuisine ramen = new Cuisine("Ramen", "Delicious noodles", tokyoRegion);
        Cuisine sushi = new Cuisine("Sushi", "Fresh seafood", tokyoRegion);
        cuisineRepository.save(ramen);
        cuisineRepository.save(sushi);
        cuisineRepository.flush();
    }

    @Test
    void getCuisinesByRegion_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/cuisines/byRegion/Tokyo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Ramen"))
                .andExpect(jsonPath("$[1]").value("Sushi"));
    }

    

    @Test
    void getCuisineByName_shouldReturnCuisine() throws Exception {
        mockMvc.perform(get("/api/cuisines/Ramen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuisineName").value("Ramen"))
                .andExpect(jsonPath("$.description").value("Delicious noodles"));
    }
}