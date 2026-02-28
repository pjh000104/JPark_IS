package com.example.localcuisine.Location;

import com.example.localcuisine.Location.domain.LocationRepository;
import com.example.localcuisine.entity.Region;
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
class LocationApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void setup() {
        locationRepository.save(new Region("Tokyo", "Japan"));
        locationRepository.save(new Region("Osaka", "Japan"));
    }

    @Test
    void getAllRegions_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/locations/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Tokyo"))
                .andExpect(jsonPath("$[1]").value("Osaka"));
    }

    @Test
    void searchRegions_shouldReturnFilteredList() throws Exception {
        mockMvc.perform(get("/api/locations/Tokyo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Tokyo"));
    }
}
