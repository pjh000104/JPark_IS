package com.example.localcuisine.Review;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.localcuisine.Cuisine.domain.CuisineRepository;
import com.example.localcuisine.Review.api.dto.CreateReviewRequest;
import com.example.localcuisine.Review.api.dto.ReviewResponse;
import com.example.localcuisine.Review.domain.ReviewRepository;
import com.example.localcuisine.Review.service.ReviewService;
import com.example.localcuisine.entity.Cuisine;
import com.example.localcuisine.entity.Review;
import com.example.localcuisine.cache.CacheService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CuisineRepository cuisineRepository;

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private ReviewService reviewService;

    private Cuisine cuisine;

    @BeforeEach
    void setup() {
        cuisine = new Cuisine();
        ReflectionTestUtils.setField(cuisine, "id", 1L);
    }

    // ---------------------------
    // createReview - success
    // ---------------------------

    @Test
    void shouldCreateReviewAndEvictCache() {

        CreateReviewRequest request = new CreateReviewRequest();
        request.setCuisineId(1L);
        request.setRating(5);
        request.setComment("Amazing");

        when(cuisineRepository.findById(1L)).thenReturn(Optional.of(cuisine));

        Review savedReview = new Review(cuisine, 5, "Amazing");
        ReflectionTestUtils.setField(savedReview, "id", 10L);

        when(reviewRepository.save(any())).thenReturn(savedReview);

        ReviewResponse response = reviewService.createReview(request);

        assertEquals(10L, response.getId());
        assertEquals(5, response.getRating());
        assertEquals("Amazing", response.getComment());

        verify(reviewRepository).save(any());
        verify(cacheService).evict("cuisine:1:reviews");
    }

    // ---------------------------
    // createReview - cuisine not found
    // ---------------------------

    @Test
    void shouldThrowException_whenCuisineNotFoundOnCreate() {

        CreateReviewRequest request = new CreateReviewRequest();
        request.setCuisineId(999L);
        request.setRating(5);
        request.setComment("No Cuisine");

        when(cuisineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reviewService.createReview(request));

        verify(reviewRepository, never()).save(any());
        verify(cacheService, never()).evict(any());
    }

    // ---------------------------
    // getReviewsByCuisine - cache hit
    // ---------------------------

    @Test
    void shouldReturnReviewsFromCache_whenCacheHit() {

        List<ReviewResponse> cachedList = List.of(
                new ReviewResponse(1L, 5, "Great", LocalDateTime.now())
        );

        when(cacheService.get("cuisine:1:reviews")).thenReturn(cachedList);

        List<ReviewResponse> result = reviewService.getReviewsByCuisine(1L);

        assertEquals(1, result.size());

        verify(reviewRepository, never()).findByCuisineId(any());
        verify(cacheService, never()).put(any(), any(), anyLong());
    }

    // ---------------------------
    // getReviewsByCuisine - cache miss
    // ---------------------------

    @Test
    void shouldFetchReviewsFromRepositoryAndCache_whenCacheMiss() {

        when(cacheService.get("cuisine:1:reviews")).thenReturn(null);

        Review review = new Review(cuisine, 5, "Nice");
        ReflectionTestUtils.setField(review, "id", 10L);

        when(reviewRepository.findByCuisineId(1L)).thenReturn(List.of(review));

        List<ReviewResponse> result = reviewService.getReviewsByCuisine(1L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getId());

        verify(reviewRepository).findByCuisineId(1L);
        verify(cacheService).put(eq("cuisine:1:reviews"), any(), eq(300L));
    }

    // ---------------------------
    // deleteReview - success
    // ---------------------------

    @Test
    void shouldDeleteReviewAndEvictCache() {

        Review review = new Review(cuisine, 5, "Nice");
        ReflectionTestUtils.setField(review, "id", 10L);

        when(reviewRepository.findById(10L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(10L);

        verify(reviewRepository).delete(review);
        verify(cacheService).evict("cuisine:1:reviews");
    }

    // ---------------------------
    // deleteReview - review not found
    // ---------------------------

    @Test
    void shouldThrowException_whenReviewNotFoundOnDelete() {

        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reviewService.deleteReview(999L));

        verify(reviewRepository, never()).delete(any());
        verify(cacheService, never()).evict(any());
    }
}