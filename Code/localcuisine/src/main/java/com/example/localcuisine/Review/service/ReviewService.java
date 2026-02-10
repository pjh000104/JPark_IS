package com.example.localcuisine.Review.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.localcuisine.entity.Cuisine;
import com.example.localcuisine.entity.Review;
import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.Review.api.dto.CreateReviewRequest;
import com.example.localcuisine.Review.api.dto.ReviewResponse;
import com.example.localcuisine.Review.domain.ReviewRepository;
import com.example.localcuisine.cache.CacheService;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CuisineRepository cuisineRepository;
    private final CacheService cacheService;

    public ReviewService(
            ReviewRepository reviewRepository,
            CuisineRepository cuisineRepository,
            CacheService cacheService
    ) {
        this.reviewRepository = reviewRepository;
        this.cuisineRepository = cuisineRepository;
        this.cacheService = cacheService;
    }

    public ReviewResponse createReview(CreateReviewRequest request) {

        Cuisine cuisine = cuisineRepository.findById(request.getCuisineId())
                .orElseThrow(() -> new IllegalArgumentException("Cuisine not found"));

        Review review = new Review(
                cuisine,
                request.getRating(),
                request.getComment()
        );

        Review saved = reviewRepository.save(review);

        String cacheKey = "cuisine:" + cuisine.getId() + ":reviews";
        cacheService.evict(cacheKey);

        return new ReviewResponse(
                saved.getId(),
                saved.getRating(),
                saved.getComment(),
                saved.getCreatedAt()
        );
    }

    public List<ReviewResponse> getReviewsByCuisine(Long cuisineId) {

        String cacheKey = "cuisine:" + cuisineId + ":reviews";

        Object cached = cacheService.get(cacheKey);
        if (cached != null) {
            System.out.println("cache hit");
            return (List<ReviewResponse>) cached;
        }

        List<ReviewResponse> responses =
                reviewRepository.findByCuisineId(cuisineId)
                        .stream()
                        .map(r -> new ReviewResponse(
                                r.getId(),
                                r.getRating(),
                                r.getComment(),
                                r.getCreatedAt()
                        ))
                        .collect(Collectors.toList());
        
        cacheService.put(cacheKey, responses, 300); 
        System.out.println("cache miss");
        return responses;
    }


    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        Long cuisineId = review.getCuisine().getId();

        reviewRepository.delete(review);

        cacheService.evict("cuisine:" + cuisineId + ":reviews");
    }
}