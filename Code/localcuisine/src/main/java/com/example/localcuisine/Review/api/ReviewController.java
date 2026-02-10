package com.example.localcuisine.Review.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.localcuisine.Review.api.dto.CreateReviewRequest;
import com.example.localcuisine.Review.api.dto.ReviewResponse;
import com.example.localcuisine.Review.service.ReviewService;

import jakarta.validation.Valid; 

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponse createReview(@RequestBody @Valid CreateReviewRequest request) {
        return reviewService.createReview(request);
    }
    
    @GetMapping("/cuisine/{cuisineId}")
    public List<ReviewResponse> getReviewsByCuisine(@PathVariable Long cuisineId) {
        return reviewService.getReviewsByCuisine(cuisineId);
    }
}
