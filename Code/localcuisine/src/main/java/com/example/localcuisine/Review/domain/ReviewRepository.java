package com.example.localcuisine.Review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.localcuisine.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCuisineId(Long cuisineId);
}