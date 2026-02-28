package com.example.localcuisine.Review;

import com.example.localcuisine.Review.api.dto.CreateReviewRequest;
import com.example.localcuisine.Review.domain.ReviewRepository;
import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.Location.domain.LocationRepository;
import com.example.localcuisine.entity.Cuisine;
import com.example.localcuisine.entity.Region;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ReviewApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Cuisine ramen;

    @BeforeEach
    void setup() {
        Region tokyo = new Region("Tokyo", "Japan");
        locationRepository.save(tokyo);
        ramen = new Cuisine("Ramen", "Delicious noodles", tokyo);
        cuisineRepository.save(ramen);
    }

    @Test
    void createReview_shouldReturnReviewResponse() throws Exception {
        CreateReviewRequest request = new CreateReviewRequest();
        request.setCuisineId(ramen.getId());
        request.setRating(5);
        request.setComment("Amazing");

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Amazing"));
    }

    @Test
    void getReviewsByCuisine_shouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/api/reviews/cuisine/" + ramen.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
